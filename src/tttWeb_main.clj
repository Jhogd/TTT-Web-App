(ns tttWeb-main
  (:require
    [ttt-web-helper :as helper])
  (:import (ogden.jake.http GuessingGameHtml Server WelcomePage pingResponse)
           ))
;(import ttt-web-helper ServeTTT)




(defn -main[& args]
  (let [server (Server/commandParse  args)
        handler (.getHandler server)]
    (.addServe handler "/hello" (WelcomePage.))
    (. handler (addServe "/game" (new GuessingGameHtml)))
    (. handler (addServe "/hello" (new pingResponse)))
    (. handler (addServe "/ttt" (helper/->ServeTTT)))
    (. server (start)))
  (println "running"))