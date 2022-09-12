#lang eopl

(require "lang.rkt")                  ; for expression?
(require "store.rkt")                 ; for reference?

(provide (all-defined-out))           ; too many things to list

;;;;;;;;;;;;;;;; expressed values ;;;;;;;;;;;;;;;;

;;; an expressed value is either a number, a boolean, a procval, or a
;;; reference. 

(define-datatype expval expval?
  (num-val
   (value number?))
  (bool-val
   (boolean boolean?))
  (proc-val 
   (proc proc?))
  (ref-val
   (ref reference?))
  )

;;; extractors:

(define expval->num
  (lambda (v)
    (cases expval v
      (num-val (num) num)
      (else (expval-extractor-error 'num v)))))

(define expval->bool
  (lambda (v)
    (cases expval v
      (bool-val (bool) bool)
      (else (expval-extractor-error 'bool v)))))

(define expval->proc
  (lambda (v)
    (cases expval v
      (proc-val (proc) proc)
      (else (expval-extractor-error 'proc v)))))

(define expval->ref
  (lambda (v)
    (cases expval v
      (ref-val (ref) ref)
      (else (expval-extractor-error 'reference v)))))

(define expval-extractor-error
  (lambda (variant value)
    (eopl:error 'expval-extractors "Looking for a ~s, found ~s"
                variant value)))

;;;;;;;;;;;;;;;; procedures ;;;;;;;;;;;;;;;;

;;; Problem 1;;;

;; TODO: multi-argument proc implementation
;; Change the implementation of proc datatype
;; It should accept multiple symbols
;; Hint: You can use list-of keyword. Example usage in
;; extend-env-rec*
;; Note: To avoid errors after the implementation please uncomment
;; and comment the code in apply-env extend-env-rec* (in environments.rkt) accordingly
;; as it is stated there

(define-datatype proc proc?
  (procedure
   (bvar (list-of symbol?))
   (body expression?)
   (env environment?)))

;; TODO: extend-env* implementation
;; Define extend-env* accepting
;; Multiple symbols and references at once.
;; Note 1: After finishing implementation
;; please uncomment extend-env* part in env->list
;; to avoid errors.
;; Note 2: If you implement extend-env* different than
;; expected you might get errors in env->list
;; In that case you should change that accordingly too

(define-datatype environment environment?
  (empty-env)
  (extend-env 
   (bvar symbol?)
   (bval reference?)                 ; new for implicit-refs
   (saved-env environment?))
  ;;;; TODO: Implement extend-env* here

  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;extend-env*;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  (extend-env*
      
      (syms (list-of symbol?))
      (vals (list-of reference?))     
      (saved-env environment?)
  )
 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;extend-env*;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;




   
  ;;;;;;;;
  (extend-env-rec*
   (proc-names (list-of symbol?))
   (b-vars (list-of symbol?))
   (proc-bodies (list-of expression?))
   (saved-env environment?)))



;; env->list : Env -> List
;; used for pretty-printing and debugging
(define env->list
  (lambda (env)
    (cases environment env
      (empty-env () '())
      (extend-env (sym val saved-env)
                  (cons
                   (list sym val)              ; val is a denoted value-- a
                   ; reference. 
                   (env->list saved-env)))
;;;;; Uncomment here when you code the extend-env*
(extend-env* (syms vals saved-env)
                (cons
                (list syms vals)
                (env->list saved-env)))
;;;;;;;;;;;;;;;;;;;
      (extend-env-rec* (p-names b-vars p-bodies saved-env)
                       (cons
                        (list 'letrec p-names '...)
                        (env->list saved-env))))))

;; expval->printable : ExpVal -> List
;; returns a value like its argument, except procedures get cleaned
;; up with env->list 
(define expval->printable
  (lambda (val)
    (cases expval val
      (proc-val (p)
                (cases proc p
                  (procedure (vars body saved-env)
                             (list 'procedure vars '... (env->list saved-env)))))
      (else val))))

