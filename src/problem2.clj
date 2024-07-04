(ns problem2
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [java-time :as jt]
            [invoice-spec :as spec]))

(defn read-json-file [filename]
  (with-open [reader (io/reader filename)]
    (json/read reader :key-fn keyword)))

(defn parse-date [date-str]
  (jt/local-date "dd/MM/yyyy" date-str))

(defn transform-invoice [invoice-json]
  (let [invoice-data (:invoice invoice-json)
        issue-date-str (:issue_date invoice-data)
        customer (:customer invoice-data)
        items (:items invoice-data)
        retentions (:retentions invoice-data)]
    {:invoice/issue-date (parse-date issue-date-str)
     :invoice/customer {:customer/name (:company_name customer)
                        :customer/email (:email customer)}
     :invoice/items (map (fn [item]
                           {:invoice-item/price (double (:price item))
                            :invoice-item/quantity (double (:quantity item))
                            :invoice-item/sku (:sku item)
                            :invoice-item/taxes (map (fn [tax]
                                                       {:tax/category (keyword (clojure.string/lower-case (:tax_category tax)))
                                                        :tax/rate (double (:tax_rate tax))}) (:taxes item))}) items)}))

(defn invoice-from-file [filename]
  (let [invoice-json (read-json-file filename)
        transformed-invoice (transform-invoice invoice-json)]
    transformed-invoice))

(defn validate-invoice [filename]
  (let [invoice (invoice-from-file filename)]
    (println "Transformed Invoice:" invoice)
    (s/valid? ::spec/invoice invoice)))


(let [invoice (invoice-from-file "invoice.json")] (println "Invoice map:" invoice))


