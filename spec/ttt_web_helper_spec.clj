(ns ttt-web-helper-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.db-and-edn :refer :all]
            [ttt-web-helper :refer :all]
            [tic-tac-toe.database :refer :all]
            [tttHtmlGenerator :refer :all]
            [handle-turn :refer :all])

  (:import (java.io File)
           (java.net URI)
           (java.io ByteArrayOutputStream OutputStream)
           (ogden.jake.http directoryOrIndex)
           (ogden.jake.http GuessingGameHtml))
  )



(describe "new instance of Serve interface"
  (with-stubs)
  (it "removes bad part of request"
    (should=  "/ttt?board=1&“game-mode“=1&diff1=3&diff2=0&player=1"
              (parseUrl "/ttt?board=1&%E2%80%9Cgame-mode%E2%80%9C=1&diff1=3&diff2=0&player=1"))
    (should=  "/ttt?board=1&game-mode=1&diff1=3&diff2=0&player=1"
              (remove-double-quotes (parseUrl "/ttt?board=1&%E2%80%9Cgame-mode%E2%80%9C=1&diff1=3&diff2=0&player=1"))))

  (it "generates a map from the incoming resource"
    (insert-game 100000 1 2)
    (save-new-game "ttt?board=1&game-mode=1&diff1=3&diff2=0&player=1")
    (should= {:state [:e :e :e :e :e :e :e :e :e], :size 3, :dimension :two, :game-type :ai-vs-human, :display :gui :user-player :x}
             (:board (last-game {:file-type :db})))
    (delete-row {:table :game})
    (delete-row {:table :game})
    (delete-row {:table :board})
    )

  (it "creates file"
    (should= true (instance? File (create-file "tttDoc.html")))
    (should= true (.exists (create-file "tttDoc.html")))
    )


  (it "calls send FileResponse from server when serving the menu html doc"
    (let [out (new ByteArrayOutputStream)
          board "Select a board"
          game "Select a game mode"
          diff "Select difficulty for computer 1"
          player "Select a player"]
     (serve-menu out)
    (should-contain board (.toString out))
    (should-contain game (.toString out))
    (should-contain diff (.toString out))
    (should-contain player (.toString out))
     ))

  (it "parses the href link to get the clicked button"
    (should= 0 (clicked-move "/tttGame/pos=0"))
    (should= 8 (clicked-move "/tttGame/pos=8"))
    (should= 3 (clicked-move "/tttGame/pos=3"))
    )

  (it "handles initial game decision"
    (with-redefs [save-new-game (stub :save-new)
                  init-game-decision (stub :init-new)
                  generate-board-html (stub :board-html)]
    (let [out (new ByteArrayOutputStream)]
      (serve-initial-game out "ttt?board=1&game-mode=1&diff1=3&diff2=0&player=1")
      (should-have-invoked :save-new {:times 1})
      (should-have-invoked :init-new {:times 1})
      (should-have-invoked :board-html {:times 1})
      )))

  (it "handles game decisions"
    (with-redefs [
                  generate-board-html (stub :board-html)
                  play-game (stub :play)
                  clicked-move (stub :clicked)
                  last-game (stub :last)
                  ]
      (let [out (new ByteArrayOutputStream)]
        (serve-tic-tac-toe out "/tttGame/pos=0")
        (should-have-invoked :board-html {:times 1})
        (should-have-invoked :play {:times 1})
        (should-have-invoked :clicked {:times 1})
        (should-have-invoked :last {:times 1})
        )))

  (it "creates a type that implements serve"
    (with-redefs [serve-initial-game (stub :init)
                  serve-tic-tac-toe (stub :ttt)
                  serve-menu (stub :menu)]
      (.sendResponse (->ServeTTT) (new ByteArrayOutputStream) "/ttt")
      (should-have-invoked :init {:times 0})
      (should-have-invoked :ttt {:times 0})
      (should-have-invoked :menu {:times 1})
      ))

  (it "creates a type that implements serve"
    (with-redefs [serve-initial-game (stub :init)
                  serve-tic-tac-toe (stub :ttt)
                  serve-menu (stub :menu)]
      (.sendResponse (->ServeTTT) (new ByteArrayOutputStream) "/ttt?")
      (should-have-invoked :init {:times 1})
      (should-have-invoked :ttt {:times 0})
      (should-have-invoked :menu {:times 0})
      ))

  (it "creates a type that implements serve"
    (with-redefs [serve-initial-game (stub :init)
                  serve-tic-tac-toe (stub :ttt)
                  serve-menu (stub :menu)]
      (.sendResponse (->ServeTTT) (new ByteArrayOutputStream) "/tttGame")
      (should-have-invoked :init {:times 0})
      (should-have-invoked :ttt {:times 1})
      (should-have-invoked :menu {:times 0})
      ))

  )