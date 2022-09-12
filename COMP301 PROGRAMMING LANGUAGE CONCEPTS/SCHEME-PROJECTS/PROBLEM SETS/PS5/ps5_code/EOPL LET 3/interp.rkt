#lang eopl

;; interpreter for the LET language.  The \commentboxes are the
;; latex code for inserting the rules into the code in the book.
;; These are too complicated to put here, see the text, sorry.

(require "lang.rkt")
(require "data-structures.rkt")
(require "environments.rkt")

(provide value-of-program value-of)

;;;;;;;;;;;;;;;; the interpreter ;;;;;;;;;;;;;;;;

;; value-of-program : Program -> ExpVal
;; Page: 71
(define value-of-program 
  (lambda (pgm)
    (cases program pgm
      (a-program (exp1)
                 (value-of exp1 (init-env))))))

;; value-of : Exp * Env -> ExpVal
;; Page: 71
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

      ; Part A
      ;;;;; implement value-of for minus operation ;;;;;;;
     (minus-exp (exp1)
        (let((val1 (value-of exp1 env)))
          (let((num1 (expval->num val1)))
            (num-val (- num1))))
        )
                    
     (increment-exp (my_num)
        (let((value1 (value-of my_num env)))
          (let((num1 (expval->num value1)))
            (num-val (+ 1 num1))))
        )

      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      
      ; Part B
      ;;;;; implement value-of for increment operation ;;;
       (factorial-exp (my_num)
        (let((value1 (value-of my_num env)))
          (let((num1 (expval->num value1)))
            (num-val (factorial-helper-func num1))))
        )

      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      ; Part C
      ;;;;; implement value-of for factorial operation ;;;
       (exponential-exp (my_num base_num)
        (let((value1 (value-of my_num env)))
        (let((value2 (value-of base_num env)))
          (let((num1 (expval->num value1)))
          (let((num2 (expval->num value2)))
            (num-val (exponential-helper-func num1 num2))))
        )))
     

      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      
      ; Part D
      ;;;;; implement value-of for exponential operation ;


      ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
      )))
;; You can add helper functions for factorial and exponential here ;;
(define factorial-helper-func
  (lambda(my_num)
    (if (= my_num 0)
      1
      (* my_num (factorial-helper-func (- my_num 1))))))

(define exponential-helper-func
  (lambda(my_number base_num)
    (if (= base_num 0)
      1
      (* my_number (exponential-helper-func my_number (- base_num 1)))))) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
