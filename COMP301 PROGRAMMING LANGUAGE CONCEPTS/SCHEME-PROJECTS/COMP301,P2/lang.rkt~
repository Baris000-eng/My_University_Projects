#lang eopl

;; grammar for the LET language  

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
    ;;; specify lexical-spec for strings
    (string ("'" letter (arbno (or letter digit))"'") string)
    (boolean ("#" (or "true" "false")) string)
   ))

(define the-grammar
  '((program (expression) a-program)
    
    (expression (number) const-exp)
    
    ;; grammar for strings


    ;; grammar for bool


    ;; grammar for comp-exp


    ;; grammar for op-exp


    ;; grammar for zero?-exp


    ;; grammar for if-exp
    (expression
     ("if" expression "then" expression "else" expression)
     if-exp)

    
    ;; grammar for my-cond-exp


    ;; grammar for var-exp


    ;; grammar for let-exp




;;;;;;;;;;;;;;;; sllgen boilerplate ;;;;;;;;;;;;;;;;

(sllgen:make-define-datatypes the-lexical-spec the-grammar)

(define show-the-datatypes
  (lambda () (sllgen:list-define-datatypes the-lexical-spec the-grammar)))

(define scan&parse
  (sllgen:make-string-parser the-lexical-spec the-grammar))

(define just-scan
  (sllgen:make-string-scanner the-lexical-spec the-grammar))

