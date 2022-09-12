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
    ;;;;edited;;;;;------------------------------------------------------------;;;;
    (expression (string) str-exp)

     (expression (identifier) var-exp)
    ;;;;edited;;;;;------------------------------------------------------------;;;;
 

     



     
    ;; grammar for bool

     (expression (boolean) bool-exp)

     
    ;; grammar for comp-exp
     (expression ("comp" "(" expression "," expression "," expression ")") comp-exp)

    ;; grammar for op-exp
    (expression ("op" "(" expression "," expression "," expression ")") op-exp)


    ;; grammar for zero?-exp
    ;;;;edited;;;;;------------------------------------------------------------;;;
    (expression
     ("zero?" "(" expression ")")
     zero?-exp)

    ;;;;edited;;;;;------------------------------------------------------------;;;;
    
    ;; grammar for if-exp
    (expression
     ("if" expression "then" expression "else" expression)
     if-exp)

    
    ;; grammar for my-cond-exp
    (expression
     ("my-cond" expression "then" expression "," (arbno expression "then" expression ",") "else" expression) my-cond)

    ;; grammar for var-exp

;;;;edited;;;;;------------------------------------------------------------;;;;
    (expression
     ("var-exp" "(" identifier ")")
     var-exp)
     
;;;;edited;;;;;------------------------------------------------------------;;;;

;;custom func start
  (expression ("pisagor-exp" "(" expression ","  expression "," expression ")") pisagor-exp)
    ;custom func end
    
    ;; grammar for let-exp
    (expression
     ("let" identifier "=" expression "in"  expression)
     let-exp )
    
    )
  )

;;;;;;;;;;;;;;;; sllgen boilerplate ;;;;;;;;;;;;;;;;

(sllgen:make-define-datatypes the-lexical-spec the-grammar)

(define show-the-datatypes
  (lambda () (sllgen:list-define-datatypes the-lexical-spec the-grammar)))

(define scan&parse
  (sllgen:make-string-parser the-lexical-spec the-grammar))

(define just-scan
  (sllgen:make-string-scanner the-lexical-spec the-grammar))

