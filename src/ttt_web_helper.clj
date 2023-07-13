(ns ttt-web-helper
  (:gen-class)
  (:require
            [tic-tac-toe.core :as core]
            [tic-tac-toe.db-and-edn :refer :all]
            [tic-tac-toe.database :as db]
            [handle-turn :refer :all]
            [tttHtmlGenerator :refer :all]
            [clojure.string :as str]
            [tic-tac-toe.algorithm :as alg]
            [hiccup2.core :as h]
            )
  (:import
    (java.net URLDecoder)
    (java.nio.charset StandardCharsets)
    (ogden.jake.http Serve)
    (java.io File)
    (ogden.jake.http GuessingGameHtml)
    (ogden.jake.http directoryOrIndex)
    ))

(defn parseUrl [resource]
  (. URLDecoder (decode resource, (.toString(. StandardCharsets UTF_8)))))

(defn remove-double-quotes [resource]
  (str/replace resource #"\“(.*?)\“" "$1"))

(defn save-new-game [resource]
  (let [game-map (. GuessingGameHtml (parseInput (remove-double-quotes (parseUrl resource))))
        board (get core/board-choice (int (get game-map "board")))
        game-mode (get core/game-mode-choice (int (get game-map "game-mode")))
        diff1 (int (get game-map "diff1"))
        diff2 (int (get game-map "diff2"))
        gameId (inc (get-game-number {:file-type :db}))
        player (get core/player-number (int (get game-map "player")))]
    (db/insert-game gameId diff1 diff2)
    (save-current-board (conj (conj (conj board {:game-type game-mode}) {:display :gui}) {:user-player player}) player gameId diff1 diff2)))


(defn create-file [file-name]
  (new File file-name))

(defn serve-menu [output]
(. directoryOrIndex (sendFileResponse  output (create-file "tttDoc.html"))))


(defn clicked-move [resource]
  (read-string (subs resource (+ (str/index-of resource "=") 1))))

(defn init-game-decision [last-state]
  (if (alg/human-turn? (:board last-state) :x (:player last-state))
    (:board last-state)
    (play-game (:board last-state) :x (:player last-state) (:game-number last-state)
               0 (:difficulty last-state)
               (:difficulty2 last-state))))

(defn serve-initial-game [out resource]
   (save-new-game resource)
  (let [last-state (last-game {:file-type :db})
        new-board (init-game-decision last-state)
        response (generate-board-html new-board)]
    (.write out (.getBytes (clojure.core/str (h/html response))))))


(defn serve-tic-tac-toe [out resource]
  (let [last-state (last-game {:file-type :db})
        user-player (:user-player (:board last-state))
        move (clicked-move resource)
       new-board (play-game (:board last-state) (:player last-state) user-player
                            (:game-number last-state) move (:difficulty last-state)
                            (:difficulty2 last-state))
       response (generate-board-html new-board)]
    (.write out (.getBytes (clojure.core/str (h/html response))))))

(deftype ServeTTT []
  Serve
  (sendResponse [this out resource]
    (cond
      (= resource "/ttt") (serve-menu out)
      (str/includes? resource "ttt?") (serve-initial-game out resource)
      :else (serve-tic-tac-toe out resource)
      )))

