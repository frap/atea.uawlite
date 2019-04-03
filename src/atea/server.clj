(ns atea.server)

;; define index content
(def home
  "<!DOCTYPE html>
<html>
  <head>
    <meta charset=\"UTF-8\">
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
    <link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">
    <link rel=\"icon\" href=\"https://clojurescript.org/images/cljs-logo-icon-32.png\">
  </head>
  <body>
    <div id=\"app\"></div>
    <script src=\"/cljs-out/dev-main.js\" type=\"text/javascript\"></script>
  </body>
</html>")

(def not-found
 "<!DOCTYPE html>
<html>
  <head>
    <meta charset=\"UTF-8\">
    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">
    <link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\">
    <link rel=\"icon\" href=\"https://clojurescript.org/images/cljs-logo-icon-32.png\">
  </head>
  <body>
  <h1>Not Found</h1>
  </body>
</html>" )

(defn handler [request]
  (if (and (= :get (:request-method request))
           (= "/"  (:uri request)))
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body home}
    {:status 404
     :headers {"Content-Type" "text/html"}
     :body not-found}))
