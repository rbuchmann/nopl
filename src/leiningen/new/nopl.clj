(ns leiningen.new.nopl
  (:use [leiningen.new.templates :only [renderer name-to-path sanitize-ns ->files]]))

(def render (renderer "nopl"))

(defn nopl [name]
  (let [data {:name name
              :ns-name (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (->files data
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["project.clj" (render "project.clj" data)])))
