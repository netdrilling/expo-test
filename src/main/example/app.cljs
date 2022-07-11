(ns example.app
  (:require [example.events]
            [example.subs]
            [example.widgets :refer [button]]
            [expo.root :as expo-root]
            ["expo-status-bar" :refer [StatusBar]]
            [re-frame.core :as rf]
            ["react-native" :as rn]
            [reagent.core :as r]
            [com.fulcrologic.fulcro.application :as app]
            [example.fulcro-app :as expo-app]
            [com.fulcrologic.fulcro-native.alpha.components :refer [react-factory ui-text ui-view]]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]))

(def shadow-splash (js/require "../assets/shadow-cljs.png"))
(def cljs-splash (js/require "../assets/cljs.png"))

(defn old-root []
  (let [counter @(rf/subscribe [:get-counter])
        tap-enabled? @(rf/subscribe [:counter-tappable?])]
    [:> rn/View {:style {:flex 1
                         :padding-vertical 50
                         :justify-content :space-between
                         :align-items :center
                         :background-color :white}}
     [:> rn/View {:style {:align-items :center}}
      [:> rn/Text {:style {:font-weight   :bold
                           :font-size     72
                           :color         :blue
                           :margin-bottom 20}} counter]
      [button {:on-press #(rf/dispatch [:inc-counter])
               :disabled? (not tap-enabled?)
               :style {:background-color :blue}}
       "Tap me, I'll count"]]
     [:> rn/View
      [:> rn/View {:style {:flex-direction :row
                           :align-items :center
                           :margin-bottom 20}}
       [:> rn/Image {:style {:width  160
                             :height 160}
                     :source cljs-splash}]
       [:> rn/Image {:style {:width  160
                             :height 160}
                     :source shadow-splash}]]
      [:> rn/Text {:style {:font-weight :normal
                           :font-size   15
                           :color       :blue}}
       "Using: shadow-cljs+expo+reagent+re-frame"]]
     [:> StatusBar {:style "auto"}]]))

(defn old-start
  {:dev/after-load true}
  []
  (expo-root/render-root (r/as-element [old-root])))

(defn old-init []
  (rf/dispatch-sync [:initialize-db])
  (old-start))

(defonce FULCRO-APP (expo-app/fulcro-app {}))

(defsc Root [this props] {}
  (ui-view {} (ui-text {} "WHAT")))

(def ui-root (comp/factory Root))

(defn ^:export init
  "Shadow-cljs sets this up to be our entry-point function. See shadow-cljs.edn `:init-fn` in the modules of the main build."
  []
  (app/mount! FULCRO-APP Root :nada))

(defn ^:export refresh
  "During development, shadow-cljs will call this on every hot reload of source. See shadow-cljs.edn"
  []
  (app/mount! FULCRO-APP Root :nada))
