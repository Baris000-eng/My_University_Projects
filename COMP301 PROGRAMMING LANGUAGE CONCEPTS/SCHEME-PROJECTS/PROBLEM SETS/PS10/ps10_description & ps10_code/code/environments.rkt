#lang eopl

(require "data-structures.rkt")
(require "store.rkt")
(provide init-env empty-env extend-env apply-env)

;;;;;;;;;;;;;;;; initial environment ;;;;;;;;;;;;;;;;

;; init-env : () -> Env
;; (init-env) builds an environment in which:
;; i is bound to a location containing the expressed value 1, 
;; v is bound to a location containing the expressed value 5, and 
;; x is bound to a location containing the expressed value 10.  
(define init-env 
  (lambda ()
    (extend-env 
     'i (newref (num-val 1))
     (extend-env
      'v (newref (num-val 5))
      (extend-env
       'x (newref (num-val 10))
       (empty-env))))))

;;;;;;;;;;;;;;;; environment constructors and observers ;;;;;;;;;;;;;;;;


;;; Problem 1 ;;;;
;;; TODO: implement cases extend-env* in apply-env here
;;; You can use location defined below and list-ref
;;; which will help in your implementation

(define apply-env
  (lambda (env search-var)
    (cases environment env
      (empty-env ()
                 (eopl:error 'apply-env "No binding for ~s" search-var))
      (extend-env (bvar bval saved-env)
                  (if (eqv? search-var bvar)
                      bval
                      (apply-env saved-env search-var)))
      ;;; TODO: Implement extend-env* here

    ;;;;;;;;;;;;;;;;;;;;;;;;;;;extend-env*;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
    (extend-env* (syms vals saved-env)
                 (if (eqv? search-var syms)
                     vals
                 (let ((k (location search-var syms)))
                      (if k
                          (newref
                             (proc-val
                                 (procedure
                                    (list-ref vals k)
                                       (list (list-ref vals k))
                                          env)
                                     )
                              
                             )
                           (apply-env saved-env search-var)))))
      ;;;,;;;;;;;;;;;;;;;;;;;;;;;;;;;;extend-env*;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                             
                                         
                                    
      
     
     




      
      ;;;;;;;;;;;;;;;;
      (extend-env-rec* (p-names b-vars p-bodies saved-env)
                       (let ((n (location search-var p-names)))
                         ;; n : (maybe int)
                         (if n
                             (newref
                              (proc-val
                               (procedure
                                ;; Procedure with one argument comment out here after
                                ;; implementation of multi-arg procedure
                                (list-ref b-vars n)   
                                ;; Procedure with multi-arg uncomment here after
                                ;; implementation of multi-arg procedure
                                (list (list-ref b-vars n))
                                ;; ;;;;;;;;;;;
                                (list-ref p-bodies n)
                                env)))
                             (apply-env saved-env search-var)))))))

;; location : Sym * Listof(Sym) -> Maybe(Int)
;; (location sym syms) returns the location of sym in syms or #f is
;; sym is not in syms.  We can specify this as follows:
;; if (memv sym syms)
;;   then (list-ref syms (location sym syms)) = sym
;;   else (location sym syms) = #f
(define location
  (lambda (sym syms)
    (cond
      ((null? syms) #f)
      ((eqv? sym (car syms)) 0)
      ((location sym (cdr syms))
       => (lambda (n) 
            (+ n 1)))
      (else #f))))

