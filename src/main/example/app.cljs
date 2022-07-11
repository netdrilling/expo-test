(ns example.app
  (:require ["react-native" :as rn]
            [com.fulcrologic.fulcro.application :as app]
            [example.fulcro-app :as expo-app]
            [com.fulcrologic.fulcro-native.alpha.components :as rncomp :refer [react-factory ui-text ui-view]]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]))

(def cljs-splash (js/require "../assets/cljs.png"))

(def ui-image (react-factory rn/Image))

(defonce FULCRO-APP (expo-app/fulcro-app {}))

(defsc Root [this props] {}
  (ui-view {} (ui-text {} "HELLO WORLD!")
           (ui-image {:style {:width  160
                                             :height 160}
                                     :source cljs-splash})))

(def ui-root (comp/factory Root))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (app/mount! FULCRO-APP Root :nada))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot reload of source. See shadow-cljs.edn"
  []
  (app/mount! FULCRO-APP Root :nada))
