#lang eopl

;; interpreter for the PROC language, using the procedural
;; representation of procedures.

;; The \commentboxes are the latex code for inserting the rules into
;; the code in the book. These are too complicated to put here, see
;; the text, sorry. 

(require "lang.rkt")
(require "data-structures.rkt")
(require "environments.rkt")

(provide value-of-program value-of)

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
      
      ; Modify proc-exp and call-exp for Problem 2
      ; Hint: var is now a list, you need value of all of them

     (double-exp (exp1 exp2)
              (let ((val1 (value-of exp1 env)))
              (let ((val2 (value-of exp2 env)))
              (let ((v1 (expval->proc val1)))
              (let ((v2 (expval->num val2)))
                   (num-val apply-procedure v1 (apply-procedure v1 (num-val v2))))))))
             
              
     
      
      (proc-exp (var body)
                (proc-val (procedure var body env)))
      
     (call-exp (rator rand)
      (let ((proc (expval->proc (value-of rator env)))
            (arg (map (lambda (my_v) (value-of my_v env))
                       rand)))
        (apply-procedure proc arg)))
      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      )))

; Old: apply-procedure : Proc * ExpVal -> ExpVal
; New: apply-procedure : Proc * ListOf(ExpVal) -> ExpVal
; Hint: You can use extend-env* from previous PS (It's already implemented for you).


(define apply-procedure
  (lambda (proc1 val)
    (cases proc proc1
      (procedure (var body saved-env) (value-of body                                      
      (cond((null? var) saved-env)
      (else (extend-env* (cdr var) (cdr val) (extend-env (car var) (car val) saved-env)))))))))
