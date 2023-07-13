(ns handle-turn
  (:require [tic-tac-toe.utility :as utility]
            [tic-tac-toe.algorithm :as alg]
            [tic-tac-toe.db-and-edn :refer :all]))

(defn play-game [board current-player player game-number move difficulty difficulty2]
  (let [new-board (if (alg/human-turn? board current-player player)
    (utility/player-move board current-player move)
    (alg/process-game-board board current-player player difficulty difficulty2))]
    (save-current-board new-board (utility/switch-player current-player) game-number difficulty difficulty2)
    new-board))
