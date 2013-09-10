package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Tweets;
import models.User;

import utils.Ref;

/**
 * Servlet implementation class getTweetsServlet
 */
@WebServlet("/GetTweetsServlet")
public class GetTweetsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DownloadFileServer download = null;
    String path;

    public void init() throws ServletException {
        super.init();
        download = new DownloadFileServer();
        download.start();
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTweetsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // while(record==null){
        // // try {
        // // Thread.sleep(1000);
        // // } catch (InterruptedException e) {
        // // e.printStackTrace();
        // // }
        // }
        // response.setContentType("application/html");
        // PrintWriter out = response.getWriter();
        // System.out.println(record[0].st);
        // out.print(record[0].st);
        // out.close();
        if(request.getParameter("request").toString().equalsIgnoreCase("speed")){
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();  
            System.out.println(download.getSpeed());
            out.print(download.getSpeed());
            out.close();    
        }else if (request.getParameter("request").toString()
                .equalsIgnoreCase("version")) {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(download.getVersion());
            out.close();
        } else if (request.getParameter("request").toString()
                .equalsIgnoreCase("top10")) {
            int id = Integer.valueOf(request.getParameter("id").toString());

            response.setContentType("application/json");

            PrintWriter out = response.getWriter();
            out.print("{\"message\":\"" + download.tweets[id].message + "\"}");
            //System.out.println(download.tweets[id].message);
            out.close();
        } else if (request.getParameter("request").toString()
                .equalsIgnoreCase("negtop10")) {
            int id = Integer.valueOf(request.getParameter("id").toString());

            response.setContentType("application/json");

            PrintWriter out = response.getWriter();
            out.print("{\"message\":\"" + download.negTweets[id].message + "\"}");
//             System.out.println(download.negTweets[id].message);
            out.close();
        } else if (request.getParameter("request").toString()
                .equalsIgnoreCase("tweets")) {
            int id = Integer.valueOf(request.getParameter("id").toString());
            response.setContentType("application/html");
            PrintWriter out = response.getWriter();
            String line = "<html><head>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    + "<link rel=\"stylesheet\" href=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css\" />"
                    + "<script src=\"http://code.jquery.com/jquery-1.8.2.min.js\"></script>"
                    + "<script src=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js\"></script>"
                    + "<meta name=\"viewport\" content=\"width=device-width,inital-scale=1, user-scalable=no\">"
                    + "</head><body>" 
                    + "<div data-role=\"page\" data-theme=\"a\" data-add-back-btn=\"true\">"
                    + "<div data-role=\"header\" data-position=\"fixed\"><h1>Tweets DashBoard</h1></div>" 
                    + "<div data-role=\"content\">"
                    + "<h1>" + download.tweets[id].ID + "</h1><hr/><br><p>"
                    + download.tweets[id].message
                    + "</p>+<p><h1>score: "+download.tweets[id].score+"</h1></p>" 
                    +"<br><b>Create at : </b> "
                    + download.tweets[id].time.toString()
                    + "<br><b>retweet count : </b>"
                    + download.tweets[id].retweet_count 
                    + "</div></div></body></html>";

            out.print(line);
            out.close();
        } else if (request.getParameter("request").toString()
                .equalsIgnoreCase("negtweets")) {
            int id = Integer.valueOf(request.getParameter("id").toString());
            response.setContentType("application/html");
            PrintWriter out = response.getWriter();
            String line = "<html><head>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    + "<link rel=\"stylesheet\" href=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css\" />"
                    + "<script src=\"http://code.jquery.com/jquery-1.8.2.min.js\"></script>"
                    + "<script src=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js\"></script>"
                    + "<meta name=\"viewport\" content=\"width=device-width,inital-scale=1, user-scalable=no\">"
                    + "</head><body>" 
                    + "<div data-role=\"page\" data-theme=\"a\" data-add-back-btn=\"true\">"
                    + "<div data-role=\"header\" data-position=\"fixed\"><h1>Tweets DashBoard</h1></div>" 
                    + "<div data-role=\"content\">"
                    + "<h1>" + download.negTweets[id].ID + "</h1><hr/><br><p>"
                    + download.negTweets[id].message
                    + "</p>+<p><h1>score: "+download.negTweets[id].score+"</h1></p>" 
                    +"<br><b>Create at : </b> "
                    + download.negTweets[id].time.toString()
                    + "<br><b>retweet count : </b>"
                    + download.negTweets[id].retweet_count 
                    + "</div></div></body></html>";

            out.print(line);
            out.close();
        } else if (request.getParameter("request").toString()
                .equalsIgnoreCase("hotUser")) {
            int id = Integer.valueOf(request.getParameter("id").toString());

            response.setContentType("application/json");

            PrintWriter out = response.getWriter();
            out.print("{\"user\":\"" + download.users[id].name + "\"}");
            // System.out.println(download.tweets[id].message);
            out.close();
        } else {
            int id = Integer.valueOf(request.getParameter("id").toString());
            response.setContentType("application/html");
            PrintWriter out = response.getWriter();
            String line = "<html><head>"
                    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                    + "<link rel=\"stylesheet\" href=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css\" />"
                    + "<script src=\"http://code.jquery.com/jquery-1.8.2.min.js\"></script>"
                    + "<script src=\"http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js\"></script>"
                    + "<meta name=\"viewport\" content=\"width=device-width,inital-scale=1, user-scalable=no\">"
                    + "</head><body>" 
                    + "<div data-role=\"page\" data-theme=\"a\" data-add-back-btn=\"true\">"
                    + "<div data-role=\"header\" data-position=\"fixed\"><h1>Tweets DashBoard</h1></div>" 
                    + "<div data-role=\"content\">"
                    + "<h1>"
                    + download.users[id].name
                    + "</h1><hr/><br><p>"
                    + download.users[id].id_str
                    + "</p><br><b>follower count : </b> "
                    + download.users[id].followers_count 
                    + "</div></div></body></html>";


            out.print(line);
            out.close();
        }

    }
}
