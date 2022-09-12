#lang eopl

;; interpreter for the PROC language, using the procedural
;; representation of procedures.

;; The \commentboxes are the latex code for inserting the rules into
;; the code in the book. These are too complicated to put here, see
;; the text, sorry. 

(require "lang.rkt")
(require "data-structures.rkt")
(require "environments.rkt")

(provide value-of-program value-of unparse-proc)

;;;;;;;;;;;;;;;; the interpreter ;;;;;;;;;;;;;;;;

;; run : String -> ExpVal
(define run
  (lambda (s)
    (value-of-program (scan&parse s))))

;; value-of-program : Program -> ExpVal
(define value-of-program 
  (lambda (pgm)
    (cases program pgm
      (a-program (exp1)
                 (value-of exp1 (init-env))))))

;; value-of : Exp * Env -> ExpVal
(define value-of
  (lambda (exp env)
    (cases expression exp
      
      ;;\commentbox{ (value-of (const-exp \n{}) \r) = \n{}}
      (const-exp (num) (num-val num))
      
      ;;\commentbox{ (value-of (var-exp \x{}) \r) = (apply-env \r \x{})}
      (var-exp (var) (apply-env env var))
      
      ;;\commentbox{\diffspec}
      (diff-exp (exp1 exp2)
                (let ((val1 (value-of exp1 env))
                      (val2 (value-of exp2 env)))
                  (let ((num1 (expval->num val1))
                        (num2 (expval->num val2)))
                    (num-val
                     (- num1 num2)))))
      
      ;;\commentbox{\zerotestspec}
      (zero?-exp (exp1)
                 (let ((val1 (value-of exp1 env)))
                   (let ((num1 (expval->num val1)))
                     (if (zero? num1)
                         (bool-val #t)
                         (bool-val #f)))))
      
      ;;\commentbox{\ma{\theifspec}}
      (if-exp (exp1 exp2 exp3)
              (let ((val1 (value-of exp1 env)))
                (if (expval->bool val1)
                    (value-of exp2 env)
                    (value-of exp3 env))))
      
      ;;\commentbox{\ma{\theletspecsplit}}
      (let-exp (var exp1 body)       
               (let ((val1 (value-of exp1 env)))
                 (value-of body
                           (extend-env var val1 env))))
     
      
      (proc-exp (var body)
                (proc-val (procedure var body env)))
      
      (call-exp (rator rand)
                (let ((proc (expval->proc (value-of rator env)))
                      (arg (value-of rand env)))
                  (apply-procedure proc arg)))
      
      )))

;;; ================= PROBLEM 1 ========================

;;; Do not make changes here
 (define unparse-proc
    (lambda(pgm)
       (cases program pgm
        (a-program (exp1)
          (unparse exp1)))))
      

;;; PROBLEM 1: Complete unparse function described below. Note that exp is an AST.
;;; (number->string 5) "5"
;;; (symbol->string 'a) "a"
;;; (string-append "Hello" "World" "!") "HelloWorld!"
;;; Note: Don't forget whitespaces!
  (define unparse
    (lambda (exp)
      (cases expression exp

        (const-exp (num) (number->string num));;;;;;;;;;;;;;;;;;;;;;;; const-exp

        (var-exp (var) (symbol->string var));;;;;;;;;;;;;;;;;;;;var-exp

        (diff-exp (exp1 exp2);;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;diff-exp
                  (let ((val1 (unparse exp1))
                        (val2 (unparse exp2)))
           (string-append "-" "(" val1 "," val2 ")")))

        (zero?-exp (exp1);;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;zero-exp
                  (let ((m1 (unparse exp1)))
                    (string-append "zero?" "(" m1 ")" )
                  

        )
                  )
              
        (if-exp (exp1 exp2 exp3);;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;if-exp
                (let
                      ((v1 (unparse exp1))
                       (v2 (unparse exp2))
                       (v3 (unparse exp3)))
              (string-append "if" v1 "then" v2 "else" v3)
                  )
                )  

        
                  
        (let-exp (var exp1 body);;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;let-exp
                 (let
                     ((b1 (symbol->string var))
                      (b2 (unparse exp1))
                      (b3 (unparse body))
                      )
                   (string-append "let" b1 "=" b2 "in" b3)))

     

        
        (proc-exp (var body);;;;;;;;;;;;;;;;;;;;proc-exp
                 (let
                     
                      (

                      (a1 (symbol->string var))
                      (a3 (unparse body))
                      )
                     
                   (string-append "proc" "(" a1 ")" a3)
                 )
               )

        (call-exp (rator rand);;;;;;;;;;;;;;;;;;call-exp
                  (let
                      ((val1 (unparse rator))
                       (val2 (unparse rand))
                      )
                   
                  (string-append "(" val1 val2 ")" ) 
                           

        )
      )
    )
  )
)
;;; ==================== END OF PROBLEM 1 ==================

(define apply-procedure
  (lambda (proc1 val)
    (cases proc proc1
      (procedure (var body saved-env)
                 (value-of body (extend-env var val saved-env))))))