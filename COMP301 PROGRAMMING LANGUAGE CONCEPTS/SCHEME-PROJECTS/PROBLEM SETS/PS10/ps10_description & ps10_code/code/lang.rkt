#lang eopl

;; language for IMPLICIT-REFS

(provide (all-defined-out))

;;;;;;;;;;;;;;;; grammatical specification ;;;;;;;;;;;;;;;;

(define the-lexical-spec
  '((whitespace (whitespace) skip)
    (comment ("%" (arbno (not #\newline))) skip)
    (identifier
     (letter (arbno (or letter digit "_" "-" "?")))
     symbol)
    (number (digit (arbno digit)) number)
    (number ("-" digit (arbno digit)) number)
    ))

(define the-grammar
  '((program (expression) a-program)
    
    (expression (number) const-exp)
    (expression
     ("-" "(" expression "," expression ")")
     diff-exp)
    
    (expression
     ("zero?" "(" expression ")")
     zero?-exp)
    
    (expression
     ("if" expression "then" expression "else" expression)
     if-exp)
    
    (expression (identifier) var-exp)

    ;; Problem 2 ;;;
    ;; Change the implementation of let grammar to new one
    ;; given in pdf.
    ;; Hint: Use separated-list
    ;;;;;;;new grammar for the let-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   (expression ("let" (arbno identifier "=" expression) "in" expression)                      
                     
                let-exp);;;;;;new grammar for let-exp
   ;;;;;;;new-grammar for the let-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    
    ;; Problem 3 ;;;
    ;; implement the let*-exp grammar which is given in pdf
    ;; Hint: Use separated-list

    ;;;;;;;;;new grammar for the let*-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   (expression ("let*" (arbno identifier "=" expression) "in" expression)                         
               let*-exp);;;;;;;;;;; new grammar for let*-exp
   ;;;;;;;;;new grammar for the let*-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



 
    ;;; TODO: Code here






    ;;;;;;;

    ;;;; Problem 1 ;;;;
    ;; Change the implementation of proc grammar to new one
    ;; given in pdf.
    ;; Hint: Use separated-list
    (expression ("proc" "(" (separated-list identifier ",") ")"
                        expression) proc-exp)

    ;; Change the implementation of call grammar
    ;; to new one given in pdf.
    ;;;;;;;;;;;;;;;;;;new grammar for the call-exp;;;;;;;;;;;;;;;;;;;;
   (expression ("(" expression (arbno expression) ")")
               call-exp);;; new grammar for the call-exp
    ;;;;;;;;;;;;;;;;;;new grammar for the call-exp;;;;;;;;;;;;;;;;;;;;;

   
    (expression
     ("letrec"
      (arbno identifier "(" identifier ")" "=" expression)
      "in" expression)
     letrec-exp)
    
    (expression
     ("begin" expression (arbno ";" expression) "end")
     begin-exp)
    
    ;; new for implicit-refs
    
    (expression
     ("set" identifier "=" expression)
     assign-exp)

     ;;; Problem 4 ;;;;;;;
    ;; TODO: implement the grammar of setdyn-exp according to pdf
    ;;;;;;;;;;;;;;;;;new grammar for the setdyn-exp;;;;;;;;;;;;;;;;;;;;;;
  (expression ("setdynamic" (arbno identifier "=" expression) "during" expression) setdyn-exp)
     ;;;;;;;;;;;;;;;;;new grammar for the setdyn-exp;;;;;;;;;;;;;;;;;;;;;;


    
     ;;;;;;;;;;;;;;;;;
    ))

;;;;;;;;;;;;;;;; sllgen boilerplate ;;;;;;;;;;;;;;;;

(sllgen:make-define-datatypes the-lexical-spec the-grammar)

(define show-the-datatypes
  (lambda () (sllgen:list-define-datatypes the-lexical-spec the-grammar)))

(define scan&parse
  (sllgen:make-string-parser the-lexical-spec the-grammar))

(define just-scan
  (sllgen:make-string-scanner the-lexical-spec the-grammar))

