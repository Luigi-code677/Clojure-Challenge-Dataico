(ns Problem3 (:require [clojure.test :refer :all]
                      [invoice-item :as item]))

(deftest test-subtotal-no-discount
  (is (= 100 (int (item/subtotal {:invoice-item/precise-quantity 2, :invoice-item/precise-price 50})))))

(deftest test-subtotal-with-discount
  (is (= 81 (int (item/subtotal {:invoice-item/precise-quantity 3, :invoice-item/precise-price 30, :invoice-item/discount-rate 10})))))

(deftest test-subtotal-zero-price
  (is (= 0 (int (item/subtotal {:invoice-item/precise-quantity 5, :invoice-item/precise-price 0})))))

(deftest test-subtotal-zero-quantity
  (is (= 0 (int (item/subtotal {:invoice-item/precise-quantity 0, :invoice-item/precise-price 100})))))

(deftest test-subtotal-zero-quantity-and-price
  (is (= 0 (int (item/subtotal {:invoice-item/precise-quantity 0, :invoice-item/precise-price 0})))))

(deftest test-subtotal-negative-quantity
  (is (= -200 (int (item/subtotal {:invoice-item/precise-quantity -4, :invoice-item/precise-price 50})))))

(deftest test-subtotal-negative-price
  (is (= -120 (int (item/subtotal {:invoice-item/precise-quantity 3, :invoice-item/precise-price -40})))))

(deftest test-subtotal-discount-zero
  (is (= 150 (int (item/subtotal {:invoice-item/precise-quantity 2, :invoice-item/precise-price 75, :invoice-item/discount-rate 0})))))

(deftest test-subtotal-discount-100
  (is (= 0 (int (item/subtotal {:invoice-item/precise-quantity 1, :invoice-item/precise-price 100, :invoice-item/discount-rate 100})))))

(deftest test-subtotal-decimal-price-and-quantity
  (is (= 75 (int (item/subtotal {:invoice-item/precise-quantity 1.5, :invoice-item/precise-price 50})))))

(deftest test-subtotal-decimal-discount-rate
  (is (= 143 (int (item/subtotal {:invoice-item/precise-quantity 2, :invoice-item/precise-price 90, :invoice-item/discount-rate 20.5})))))

