(ns {{ns-name}}.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs.nodejs :as node]
            [cljs.core.async :refer [<! put! chan] :as async]
            [cljs.tools.cli :refer [parse-opts]]))

(node/enable-util-print!)

(def readline (node/require "readline"))

(defn stdin->chan [c]
  (doto (.createInterface readline
                          (clj->js {:input (.-stdin js/process)
                                    :terminal false}))
    (.on "line" #(put! c %))
    (.on "close" #(async/close! c)))
  c)

(def cli-options
  ;; An option with a required argument
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(js/parseInt %)
    :validate [#(< 0 % 65536) "Must be a number between 0 and 65536"]]
   ;; A non-idempotent option
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main [& args]
  (let [stdin (stdin->chan (chan))]
    (go (println (<! stdin))))
  (println "Opts:" (parse-opts args cli-options)))

(set! *main-cli-fn* -main)
