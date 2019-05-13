package ru.javaops.masterjava.upload;

import javafx.util.Pair;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static ru.javaops.masterjava.common.web.ThymeleafListener.engine;

@WebServlet(urlPatterns = "/", loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10) //10 MB in memory limit
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        webContext.setVariable("chankSize", -1);
        engine.process("upload", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final WebContext webContext = new WebContext(req, resp, req.getServletContext(), req.getLocale());
        CompletionService<Pair<String, List<String>>> service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(8));
        try {
//          http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
            Part filePart = req.getPart("fileToUpload");
            if (filePart.getSize() == 0) {
                throw new IllegalStateException("Upload file have not been selected");
            }
            final String param = req.getParameter("chankSize");
            final int chankSize = Integer.parseInt(param != null ? param : "-1");

            try (InputStream is = filePart.getInputStream()) {
                final UserProcessor processor = new UserProcessor(is);
                int submitted = 0;
                List<User> users = processor.process(chankSize);
                while (!users.isEmpty()) {
                    service.submit(callChank(users, chankSize));
                    users = processor.process(chankSize);
                    submitted++;
                }

                List<Pair<String, List<String>>> result = new ArrayList<>();
                while (submitted-- > 0) {
                    result.add(service.take().get());
                }

                webContext.setVariable("pairs", result);
                engine.process("result", webContext, resp.getWriter());
            }
        } catch (Exception e) {
            webContext.setVariable("exception", e);
            engine.process("exception", webContext, resp.getWriter());
        }
    }


    private Callable<Pair<String, List<String>>> callChank(final List<User> users, final int chankSize) {
        final UserDao dao = DBIProvider.getDao(UserDao.class);
            return () -> {
                int[] insertResults;
                try {
                    insertResults = dao.insertBatch(users, chankSize);
                } catch (Exception e) {
                    return new Pair<>(e.getMessage(), users.stream().map(User::getEmail).collect(Collectors.toList()));
                }
                Pair<String, List<String>> pair = new Pair<>(
                        users.get(0).getEmail() + " - " + users.get(users.size() - 1).getEmail() + ":",
                        new ArrayList<>()
                );
                for (int i = 0; i < insertResults.length; i++) {
                    if (insertResults[i] == 0) {
                        pair.getValue().add(users.get(i).getEmail());
                    }
                }
                return pair;
            };
    }
}
