#lang eopl

;; builds environment interface, using data structures defined in
;; data-structures.scm. 

(require "data-structures.rkt")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PROBLEM 2 ;;;;;;;;;;;;;;;;;;;;
(provide init-env empty-env extend-env apply-env extend-env-rec*) ; we added extend-env-rec* here
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;;;;;;;;;;;;;;; initial environment ;;;;;;;;;;;;;;;;

;; init-env : () -> Env
;; usage: (init-env) = [i=1, v=5, x=10]
;; (init-env) builds an environment in which i is bound to the
;; expressed value 1, v is bound to the expressed value 5, and x is
;; bound to the expressed value 10.
;; Page: 69

(define init-env 
  (lambda ()
    (extend-env 
     'i (num-val 1)
     (extend-env
      'v (num-val 5)
      (extend-env
       'x (num-val 10)
       (empty-env))))))

;;;;;;;;;;;;;;;; environment constructors and observers ;;;;;;;;;;;;;;;;

;; Page: 86
(define apply-env
  (lambda (env search-sym)
    (cases environment env
      (empty-env ()
                 (eopl:error 'apply-env "No binding for ~s" search-sym))
      (extend-env (var val saved-env)
                  (if (eqv? search-sym var)
                      val
                      (apply-env saved-env search-sym)))
	  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PROBLEM 2 ;;;;;;;;;;;;;;;;;;;;
	  ; modify this to be extend-env-rec*
          ; extend-env-rec* calls apply-extend-env-rec*,
          ; it extends the environment recursively for more than one name, var and body
       (extend-env-rec* (p-name b-var p-body saved-env) (let my_k ((my_pn p-name) (my_bv b-var) (my_pb p-body))
                                                                  (cond ((equal? '() my_pn) (apply-env saved-env search-sym))
                                                                  ((equal? search-sym '())  saved-env)
                                                                  ((equal? p-body '()) saved-env)
                                                                  ((equal? b-var '()) saved-env)
                                                                  ((equal? search-sym (car my_pn)) (proc-val (procedure (car my_bv) (car my_pb) env)))
                                                                  (else (my_k (cdr my_pn) (cdr my_bv) (cdr my_pb))))))
                                                                          
                                                           
	  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; PROBLEM 2 ;;;;;;;;;;;;;;;;;;;;
; Implement apply-extend-env-rec* here. This is also called by extend-env-rec*
; It starts like this:
;(define apply-extend-env-rec*
;(lambda ( env p-name b-var body saved-env search-var )
   ;(procedure (b-var body saved-env) (proc-val body                                   
   ; (cond((null? search-var) saved-env)
     ; (else (apply-extend-env-rec* p-name (cdr b-var) (cdr search-var) (apply-extend-env-rec* p-name (car b-var) (car search-var) saved-env))))))))

    

        
 ;;    ))
     
    
; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;