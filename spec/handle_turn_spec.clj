(ns handle-turn-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.utility :refer :all]
            [handle-turn :refer :all]
            [tic-tac-toe.database :refer :all]
            [tic-tac-toe.algorithm :refer :all])
  )

(def test-board (conj (conj (init-board(->Three-by-three)) {:display :gui}) {:game-type :ai-vs-human}))

(describe "handles player turn and returns a board for html generation"
  (with-stubs)

  (it "plays a turn"
    (should= {:state [:e :e :x :e :e :e :e :e :e], :size 3, :dimension :two, :display :gui, :game-type :ai-vs-human}
             (play-game test-board X X  100 2 1 1))
    (delete-row {:table :board}))

  (it "plays another turn"
    (with-redefs [best-move (stub 4)]
    (should= {:state [:e :e :e :e :x :e :e :e :e], :size 3, :dimension :two, :display :gui, :game-type :ai-vs-human}
             (play-game test-board X X  100 4 1 1))
    (delete-row {:table :board})))

  )