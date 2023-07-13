(ns tttHtmlGenerator-spec
  (:require [speclj.core :refer :all]
            [tttHtmlGenerator :refer :all]
            [tic-tac-toe.utility :refer :all]
          ))


(describe "html helper that spits html"
  (with-stubs)
  (it "converts keyword :x and :o to X and O and :e to " ""
    (should= "X" (key-to-string :x))
    (should= "O" (key-to-string :o))
    (should= "" (key-to-string :e))
    )


  (it "creates an html page for displaying a board"
    (with-redefs [key-to-string (stub :key2s)]
      (should= [:html [:head [:title "Tic Tac Toe"] [:style ".board {\n            display: grid;\n            grid-template-columns: repeat(3, 100px);\n            gap: 10px;\n        }\n\n        .square {\n            width: 100px;\n            height: 100px;\n            font-size: 24px;\n            font-weight: bold;\n            background-color:pink;\n        }"]] [:body [:div.board [:a {:href "/tttGame/pos=0"} [:button.square nil]] [:a {:href "/tttGame/pos=1"} [:button.square nil]] [:a {:href "/tttGame/pos=2"} [:button.square nil]] [:a {:href "/tttGame/pos=3"} [:button.square nil]] [:a {:href "/tttGame/pos=4"} [:button.square nil]] [:a {:href "/tttGame/pos=5"} [:button.square nil]] [:a {:href "/tttGame/pos=6"} [:button.square nil]] [:a {:href "/tttGame/pos=7"} [:button.square nil]] [:a {:href "/tttGame/pos=8"} [:button.square nil]] [:a {:href "/ttt"} [:button.square "New Game"]]]]]
                      (generate-board-html (init-board (->Three-by-three)))
                      )
      (should-have-invoked :key2s {:times 9})))


(it "creates an html page for displaying a four by four board"
  (with-redefs [key-to-string (stub :key2s)]
    (should= [:html [:head [:title "Tic Tac Toe"] [:style ".board {\n            display: grid;\n            grid-template-columns: repeat(4, 100px);\n            gap: 10px;\n        }\n\n        .square {\n            width: 100px;\n            height: 100px;\n            font-size: 24px;\n            font-weight: bold;\n            background-color:pink;\n        }"]] [:body [:div.board [:a {:href "/tttGame/pos=0"} [:button.square nil]] [:a {:href "/tttGame/pos=1"} [:button.square nil]] [:a {:href "/tttGame/pos=2"} [:button.square nil]] [:a {:href "/tttGame/pos=3"} [:button.square nil]] [:a {:href "/tttGame/pos=4"} [:button.square nil]] [:a {:href "/tttGame/pos=5"} [:button.square nil]] [:a {:href "/tttGame/pos=6"} [:button.square nil]] [:a {:href "/tttGame/pos=7"} [:button.square nil]] [:a {:href "/tttGame/pos=8"} [:button.square nil]] [:a {:href "/tttGame/pos=9"} [:button.square nil]] [:a {:href "/tttGame/pos=10"} [:button.square nil]] [:a {:href "/tttGame/pos=11"} [:button.square nil]] [:a {:href "/tttGame/pos=12"} [:button.square nil]] [:a {:href "/tttGame/pos=13"} [:button.square nil]] [:a {:href "/tttGame/pos=14"} [:button.square nil]] [:a {:href "/tttGame/pos=15"} [:button.square nil]] [:a {:href "/ttt"} [:button.square "New Game"]]]]]
             (generate-board-html (init-board (->Four-by-four)))
             )
    (should-have-invoked :key2s {:times 16})))
)

