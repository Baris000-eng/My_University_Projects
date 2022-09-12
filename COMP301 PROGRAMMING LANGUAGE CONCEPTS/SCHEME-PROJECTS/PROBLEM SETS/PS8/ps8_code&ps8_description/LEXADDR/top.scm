(module top (lib "eopl.ss" "eopl")
  
  ;; top level module.  Loads all required pieces.
  ;; Run the test suite with (run-all).

  (require "drscheme-init.scm")
  (require "data-structures.scm")  ; for expval constructors
  (require "lang.scm")             ; for scan&parse
  (require "interp.scm")           ; for value-of-program
  (require "tests.scm")            ; for test-list
  (require "translator.scm")	   ; for translation-of-program
  
  (provide run run-all)
  
  ;;;; function for automated testing ;;;;
  (provide test-all)
  (define (test-all) (run-all))


  ;;;;;;;;;;;;;;;; interface to test harness ;;;;;;;;;;;;;;;;
  
  ;; run : String -> ExpVal
  ;; Page: 98
  (define run
    (lambda (string)
      (value-of-translation
	(translation-of-program
	  (scan&parse string)))))

   (define translate
    (lambda (string)
	(translation-of-program
	  (scan&parse string))))
  
  ;; run-all : () -> Unspecified

  ;; runs all the tests in test-list, comparing the results with
  ;; equal-answer?  

  (define run-all
    (lambda ()
      (run-tests! run equal-answer? test-list)))
  
  (define equal-answer?
    (lambda (ans correct-ans)
      (equal? ans (sloppy->expval correct-ans))))
  
  (define sloppy->expval 
    (lambda (sloppy-val)
      (cond
        ((number? sloppy-val) (num-val sloppy-val))
        ((boolean? sloppy-val) (bool-val sloppy-val))
        (else
         (eopl:error 'sloppy->expval 
                     "Can't convert sloppy value to expval: ~s"
                     sloppy-val)))))
    
  ;; run-one : Sym -> ExpVal

  ;; (run-one sym) runs the test whose name is sym
  
  (define run-one
    (lambda (test-name)
      (let ((the-test (assoc test-name test-list)))
        (cond
          ((assoc test-name test-list)
           => (lambda (test)
                (run (cadr test))))
          (else (eopl:error 'run-one "no such test: ~s" test-name))))))


  (display "\nFor problem 2, you will observe unparsed abstract syntax trees in Lexical addressing")
  (display "\nAs expected, you will the lexical addresses of declared variables, i.e. #0 or #1 regarding their lexical depth.\n")
  (display "Please, also check lang.scm for nameless-let and nameless-proc, they are needed to be defined as lexlet and lexproc.\n\n")

;  (display "\n=========== TEST CASES =============\n")
;  
;  (display (unparse-nameless (translate "let x = 10 in if zero?(x) then -(x, 10) else let y = 20 in proc(a) -(a, -(x,y))")))
;  (display "\n")
;  (display (unparse-nameless (translate "if zero?(-(5,3)) then let a = 1 in -(a,1) else let a = proc(x) -(x, 10) in (a 5)")))
;  (display "\n")
;  (display (unparse-nameless (translate "let x = proc(a) if zero?(-(a,1)) then -(a, -10) else -(-10, a) in let y = 10 in (x y)")))

;  =========== OUTPUTS =============
;  lexlet 10 in if zero?(#0) then -(#0,10) else lexlet 20 in lexproc -(#0,-(#2,#1))
;  
;  if zero?(-(5,3)) then lexlet 1 in -(#0,1) else lexlet lexproc -(#0,10) in (#0 5)
;  
;  lexlet lexproc if zero?(-(#0,1)) then -(#0,-10) else -(-10,#0) in lexlet 10 in (#1 #0)

  
  )




