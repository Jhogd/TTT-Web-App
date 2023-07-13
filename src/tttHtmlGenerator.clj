(ns tttHtmlGenerator
  )

(def key-string {:x "X" :o "O" :e ""})

(defn key-to-string [marker]
  (get key-string marker))

(defmulti generate-board-html :size)

(defmethod generate-board-html 3 [board]
  (let [state (:state board)]
    [:html
     [:head
      [:title "Tic Tac Toe"]
      [:style ".board {
            display: grid;
            grid-template-columns: repeat(3, 100px);
            gap: 10px;
        }

        .square {
            width: 100px;
            height: 100px;
            font-size: 24px;
            font-weight: bold;
            background-color:pink;
        }"]]
     [:body
      [:div.board
       [:a {:href "/tttGame/pos=0"} [:button.square (key-to-string (nth state 0))]]
       [:a {:href "/tttGame/pos=1"} [:button.square (key-to-string (nth state 1))]]
       [:a {:href "/tttGame/pos=2"} [:button.square (key-to-string (nth state 2))]]
       [:a {:href "/tttGame/pos=3"} [:button.square (key-to-string (nth state 3))]]
       [:a {:href "/tttGame/pos=4"} [:button.square (key-to-string (nth state 4))]]
       [:a {:href "/tttGame/pos=5"} [:button.square (key-to-string (nth state 5))]]
       [:a {:href "/tttGame/pos=6"} [:button.square (key-to-string (nth state 6))]]
       [:a {:href "/tttGame/pos=7"} [:button.square (key-to-string (nth state 7))]]
       [:a {:href "/tttGame/pos=8"} [:button.square (key-to-string (nth state 8))]]
       [:a {:href "/ttt"} [:button.square "New Game"]]]]]))

(defmethod generate-board-html 4 [board]
  (let [state (:state board)]
    [:html
     [:head
      [:title "Tic Tac Toe"]
      [:style ".board {
            display: grid;
            grid-template-columns: repeat(4, 100px);
            gap: 10px;
        }

        .square {
            width: 100px;
            height: 100px;
            font-size: 24px;
            font-weight: bold;
            background-color:pink;
        }"]]
     [:body
      [:div.board
       [:a {:href "/tttGame/pos=0"} [:button.square (key-to-string (nth state 0))]]
       [:a {:href "/tttGame/pos=1"} [:button.square (key-to-string (nth state 1))]]
       [:a {:href "/tttGame/pos=2"} [:button.square (key-to-string (nth state 2))]]
       [:a {:href "/tttGame/pos=3"} [:button.square (key-to-string (nth state 3))]]
       [:a {:href "/tttGame/pos=4"} [:button.square (key-to-string (nth state 4))]]
       [:a {:href "/tttGame/pos=5"} [:button.square (key-to-string (nth state 5))]]
       [:a {:href "/tttGame/pos=6"} [:button.square (key-to-string (nth state 6))]]
       [:a {:href "/tttGame/pos=7"} [:button.square (key-to-string (nth state 7))]]
       [:a {:href "/tttGame/pos=8"} [:button.square (key-to-string (nth state 8))]]
       [:a {:href "/tttGame/pos=9"} [:button.square (key-to-string (nth state 9))]]
       [:a {:href "/tttGame/pos=10"} [:button.square (key-to-string (nth state 10))]]
       [:a {:href "/tttGame/pos=11"} [:button.square (key-to-string (nth state 11))]]
       [:a {:href "/tttGame/pos=12"} [:button.square (key-to-string (nth state 12))]]
       [:a {:href "/tttGame/pos=13"} [:button.square (key-to-string (nth state 13))]]
       [:a {:href "/tttGame/pos=14"} [:button.square (key-to-string (nth state 14))]]
       [:a {:href "/tttGame/pos=15"} [:button.square (key-to-string (nth state 15))]]
       [:a {:href "/ttt"} [:button.square "New Game"]]]]]))


