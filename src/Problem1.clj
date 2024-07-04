(ns Problem1)

(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn is-iva-19 "Verifies if IVA is 19%" [item]
  (some #(and (= :iva (:tax/category %)) (= 19 (:tax/rate %))) (:taxable/taxes item)))

(defn is-ret-1 "Verifies if retention rate is 1" [item]
  (some #(and (= :ret_fuente (:retention/category %)) (= 1 (:retention/rate %))) (:retentionable/retentions item)))

(defn filter-invoice-items "Filters the items of the invoice" [invoice] (
                                       ->> invoice :invoice/items
                                           (filter #(or (is-iva-19 %) (is-ret-1 %)))
                                           (filter #(not (and (is-iva-19 %) (is-ret-1 %))))
                                           ))

(def filtered-items (filter-invoice-items invoice))

(println filtered-items)
