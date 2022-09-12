#lang eopl
(require eopl/tests/private/utils)

(require "data-structures.rkt")  ; for expval constructors
(require "lang.rkt")             ; for scan&parse
(require "interp.rkt")           ; for value-of-program

;; run : String -> ExpVal
;; Page: 71
(define run
  (lambda (string)
    (value-of-program (scan&parse string))))

(define equal-answer?
  (lambda (ans correct-ans)
    (equal? ans (sloppy->expval correct-ans))))

(define sloppy->expval 
  (lambda (sloppy-val)
    (cond
      ((number? sloppy-val) (num-val sloppy-val))
      ((boolean? sloppy-val) (bool-val sloppy-val))
      ((string? sloppy-val) (str-val sloppy-val))
      (else
       (eopl:error 'sloppy->expval 
                   "Can't convert sloppy value to expval: ~s"
                   sloppy-val)))))

(define-syntax-rule (check-run (name str res) ...)
  (begin
    (cond [(eqv? 'res 'error)
           (check-exn always? (lambda () (run str)))]
          [else
           (check equal-answer? (run str) 'res (symbol->string 'name))])
    ...))

;;;;;;;;;;;;;;;; tests ;;;;;;;;;;;;;;;;



(check-run


;;;;edited;;;;;------------------------------------------------------------;;;;
  
 
 
 ;; simple arithmetic
 (positive-const "11" 11)
 (zero "0" 0)



 
 ;; check environment
 (env-i "i" error)
 ;; replace these with the values you defined
 (env-x "x" 4)
 (env-y "y" 3)
 (env-z "z" 6)


 
 ;; simple unbound variables
 (test-unbound-var-1 "foo" error)


 ; simple let
 (simple-let-1 "let x = 3 in x" 3)



 ;; string tests
 (str-test "'this'" "'this'")
 (str-test2 "'test" error)


 
 ;; boolean tests
 (bool-test "#true" #t)
 (bool-test "#false" #f)
 (bool-test "#f" error)

 ;; op tests
 (op-test "op(5,'add',6)" 11)
 (op-test2 "op(5,'sub',6)" -1)
 (op-test3 "op(5,'mult',6)" 30)
 (op-test4 "op(5,'div',6)" 5/6)
 (nested-op-test "op(op(6,'add',2),'div',4)" 2)
 (nested-op-test "op(op(6,'add',2),'sub',op(-2,'mult',4))" 16)

 ;; comp tests
 (comp-test "comp(5,'greater',6)" #f)
 (comp-test2 "comp(3,'equal',3)" #t)
 (comp-test3 "comp(5,'less',6)" #t)

  ;; basic if tests
 (if-basic "if zero?(0) then 2 else 1" 2)
 (else-basic "if zero?(3) then 2 else 1" 1)

 ;; more complex if tests
 (if-complex "if comp(6,'less',7) then op(7,'mult',2) else op(5,'sub',2)" 14)
 (else-complex "if comp(6,'greater',7) then op(7,'mult',2) else if zero?(op(3,'sub',3)) then op(5,'sub',2) else 5" 3)

 ;; check nested let and shadowing
 (simple-nested-let "let x = 3 in let y = 4 in op(x,'sub',y)" -1)
 (check-shadowing-in-body "let x = 3 in let x = 4 in x" 4)

 ;; check my cond exp
(simple-my-cond "my-cond comp(1,'greater',1) then 1, else 4" 4)
 ;(simple-my-cond "my-cond comp(1,'greater',1) then 1, comp(2,'greater',2) then 2, comp(3,'equal',3) then 1, comp(4,'equal',4) then op(3,'mult',3), else 4" 9)
 ;(simple-my-cond "my-cond comp(7,'greater',5) then 7, comp(3,'equal',3) then op(3,'mult',3), else 4" 9)
; (simple-my-cond2 "my-cond comp(7,'greater',5) then 7, comp(3,'equal',4) then op(3,'mult',3), else 4" 7)
 ;(simple-my-cond-else "my-cond comp(7,'less',5) then 7, comp(3,'equal',2) then op(3,'mult',3), else 4" 4)
#|
 ;; complex test cases
 (complex-1 "let x = 6 in let y = op(3,'sub',1) in let z = comp(4,'greater', 2) in my-cond z then x, comp(x,'equal',6) then y, comp(y,'equal',x) then z, else if comp(x, 'greater', let y = 2 in op(y,'mult',2)) then x else y" 2)
 (complex-2 "let x = 6 in let y = op(3,'add',3) in let z = comp(4,'greater', 2) in my-cond z then x, comp(x,'equal',6) then y, comp(y,'equal',x) then z, else if comp(x, 'greater', let y = 2 in op(y,'mult',2)) then x else y" #t)
 (complex-3 "let x = 6 in let y = op(3,'add',1) in let z = comp(4,'less', 2) in my-cond z then x, comp(x,'equal',3) then y, comp(y,'equal',x) then z, else if comp(x, 'greater', let y = 2 in op(y,'mult',2)) then x else y" 6)
 (complex-4 "let x = 6 in let y = op(3,'add',1) in let z = comp(4,'less', 2) in my-cond z then x, comp(x,'equal',3) then y, comp(y,'equal',x) then z, else if comp(x, 'greater', let y = 2 in op(y,'mult',6)) then x else y" 4)

 ;; nested my-cond cases
 (nested-cond "let z = #true in let x = #false in my-cond x then my-cond comp(3,'less',4) then 5, else op(4,'mult',2), z then op(5,'mult',op(4,'sub',1)), else let y = 3 in op(y,'div',2)" 15)
 (nested-cond2 "let z = #true in let x = #false in my-cond let x = #true in x then my-cond comp(3,'less',4) then 5, else op(4,'mult',2), #false then op(5,'mult',op(4,'sub',1)), else let y = 3 in op(y,'div',2)" 5)
 (nested-cond3 "let z = #true in let x = #false in my-cond zero?(2) then my-cond comp(3,'less',4) then 5, else op(4,'mult',2), #false then op(5,'mult',op(4,'sub',1)), else let y = 3 in op(y,'div',2)" 3/2)
|#
 
 



(pisagor-exp "pisagor-exp(3,4,5)" #t)
(pisagor-exp "pisagor-exp(5,12,13)" #t)
(pisagor-exp "pisagor-exp(8,10,11)" #f)
(pisagor-exp "pisagor-exp(23,24,28)" #f)

 
 )
