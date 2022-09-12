#lang eopl

;; interpreter for the IMPLICIT-REFS language

(require "lang.rkt")
(require "data-structures.rkt")
(require "environments.rkt")
(require "store.rkt")

(require (only-in racket pretty-print))

(provide value-of-program value-of instrument-let instrument-newref)

;;;;;;;;;;;;;;;; switches for instrument-let ;;;;;;;;;;;;;;;;

(define instrument-let (make-parameter #f))

;; say (instrument-let #t) to turn instrumentation on.
;;     (instrument-let #f) to turn it off again.

;;;;;;;;;;;;;;;; the interpreter ;;;;;;;;;;;;;;;;

;; value-of-program : Program -> ExpVal
(define value-of-program 
  (lambda (pgm)
    (initialize-store!)
    (cases program pgm
      (a-program (exp1)
                 (value-of exp1 (init-env))))))

;; value-of : Exp * Env -> ExpVal
;; Page: 118, 119
(define value-of
  (lambda (exp env)
    (cases expression exp
      
      ;\commentbox{ (value-of (const-exp \n{}) \r) = \n{}}
      (const-exp (num) (num-val num))
      
      ;\commentbox{ (value-of (var-exp \x{}) \r) 
      ;              = (deref (apply-env \r \x{}))}
      (var-exp (var) (deref (apply-env env var)))
      
      ;\commentbox{\diffspec}
      (diff-exp (exp1 exp2)
                (let ((val1 (value-of exp1 env))
                      (val2 (value-of exp2 env)))
                  (let ((num1 (expval->num val1))
                        (num2 (expval->num val2)))
                    (num-val
                     (- num1 num2)))))
      
      ;\commentbox{\zerotestspec}
      (zero?-exp (exp1)
                 (let ((val1 (value-of exp1 env)))
                   (let ((num1 (expval->num val1)))
                     (if (zero? num1)
                         (bool-val #t)
                         (bool-val #f)))))
      
      ;\commentbox{\ma{\theifspec}}
      (if-exp (exp1 exp2 exp3)
              (let ((val1 (value-of exp1 env)))
                (if (expval->bool val1)
                    (value-of exp2 env)
                    (value-of exp3 env))))
      
      
      ;;; Problem 2;;;;

      ;; TODO: modify let-exp to taking multi assingment one
      ;; Hint 1: Use extend-env* you have implemented in Part B
      ;; Hint 2: You can use map to ease implementation

   ;;;;;;;;;let-exp implementation;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
   (let-exp (var exp body)
                      (value-of body
                                (extend-env* var (map (lambda (my_custom_exp)
                                       (newref (value-of my_custom_exp env)))
                                     exp) env)))
   ;;;;;;;;;let-exp implementation;;;;;;;;;;;;;	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

      ;;;;; Problem 3 ;;;;
      ;; TODO: implement let*-exp as described in pdf
      ;; Note: You can use helper functions
      ;; Hint 1: you will probably not use extend-env* because
      ;; in each extension should be done on the environment
      ;; extended by previous pairs. It might be good idea to
      ;; use extend-env instead
      ;; Challenge: You can implement this without using any
      ;; helper function. Think the ways you can use to
      ;; exploit the recursive nature of value-of
      ;; Hint for challenge: We mostly use extractors of expressions
      ;; you can also use constructors.
      ;;;;;;;; TODO: Code here
      ;;;;;;;;;;;;let*-exp implementation;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      (let*-exp (var exp body)
               (value-of body
                         (extend-env var (map (lambda (my_custom_exp)
                                       (newref (value-of my_custom_exp env))))
                                     env)
                
                ))
      
      ;;;;;;;;;;;;let*-exp implementation;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;    

      
      ;;;;;;;;;;;;;;;;;;

      ;; Problem 1 ;; 
      ;;; TODO: modify proc-exp to the one accepting
      ;;; multiple variables

      ;;;;;;;;;;;;modified-proc-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;    
      (proc-exp (var body)
                     (proc-val (procedure (map (lambda (m)
                                              (value-of m env))
                                               var) body env)))
       ;;;;;;;;;;;;modified-proc-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      
      ;; TODO: modify call-exp to have multiple rands.
      ;; Hint: you can use the map function to easily
      ;; find the value-of's of rands.

      ;;;;;;;;;;;;modified call-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;        
     (call-exp (rator rand)

               (apply-procedure (expval->proc (value-of rator env)) (map (lambda (my_custom_val)
                                              (value-of my_custom_val env))
                                               rand)
                                )
                               )
      ;;;;;;;;;;;modified call-exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;        
               
      
      (letrec-exp (p-names b-vars p-bodies letrec-body)
                  (value-of letrec-body
                            (extend-env-rec* p-names b-vars p-bodies env)))
      
      (begin-exp (exp1 exps)
                 (letrec 
                     ((value-of-begins
                       (lambda (e1 es)
                         (let ((v1 (value-of e1 env)))
                           (if (null? es)
                               v1
                               (value-of-begins (car es) (cdr es)))))))
                   (value-of-begins exp1 exps)))
      
      (assign-exp (var exp1)
                  (begin
                    (setref!
                     (apply-env env var)
                     (value-of exp1 env))
                    (num-val 27)))

      ;;;;; Problem 4 ;;;;;
      ;; TODO: implement the setdyn-exp
      ;; Hint: First set the values and evaluate the body but
      ;; don't return the result immediately
      ;; Then revert the values back to their original values
      ;; and return the result.
      ;;;;; TODO: Code here

   ;;;;;;;;;;;;;;;;;;;;;;set-dyn exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;    
  (setdyn-exp (my_custom_var my_custom_exp my_custom_body)
			     (begin
			       (setref! (apply-env env my_custom_var) (value-of my_custom_exp env))
				 (setref! (apply-env env my_custom_var) (deref (apply-env env my_custom_var)))
				 (value-of my_custom_body env)))
   ;;;;;;;;;;;;;;set-dyn exp;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;    









      
      ;;;;;;;;;;;
      
      )))


;; apply-procedure : Proc * ExpVal -> ExpVal
;; Page: 119

;; uninstrumented version
;;  (define apply-procedure
;;    (lambda (proc1 val)
;;      (cases proc proc1
;;        (procedure (var body saved-env)
;;          (value-of body
;;            (extend-env var (newref val) saved-env))))))


;;; Problem 1 ;;;
;; Change the implementation of apply-procedure
;; to make it suitable for multi-argument procedures
;; Hint: To easily find the value of list of arguments
;; you can use map function.

(define apply-procedure
  (lambda (proc1 arg)
    (cases proc proc1
      (procedure (var body saved-env)
                 (let ((ref (newref arg)))
                   (let ((new-env (extend-env var ref saved-env)))
                   (map (lambda (my_custom_exp)
                                       (newref (value-of my_custom_exp new-env)))
                                     body)
                     (value-of body new-env)))))))

;; store->readable : Listof(List(Ref,Expval)) 
;;                    -> Listof(List(Ref,Something-Readable))
(define store->readable
  (lambda (l)
    (map
     (lambda (p)
       (list
        (car p)
        (expval->printable (cadr p))))
     l)))




