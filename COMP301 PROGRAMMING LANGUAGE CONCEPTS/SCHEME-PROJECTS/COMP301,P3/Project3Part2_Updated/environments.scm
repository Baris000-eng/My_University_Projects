(module environments (lib "eopl.ss" "eopl") 
  
  ;; builds environment interface, using data structures defined in
  ;; data-structures.scm. 

  (require "data-structures.scm")

  (provide init-env empty-env extend-env apply-env)

;;;;;;;;;;;;;;;; initial environment ;;;;;;;;;;;;;;;;
  
  ;; init-env : () -> Env
  ;; usage: (init-env) = [i=1, v=5, x=10]
  ;; (init-env) builds an environment in which i is bound to the
  ;; expressed value 1, v is bound to the expressed value 5, and x is
  ;; bound to the expressed value 10.
  ;; Page: 69

  (define init-env 
    (lambda ()
      
      ; #####################################################
      ; ###### ENTER YOUR CODE HERE
      ; ###### you need to extend the environment with count 
      ; ###### variable so that it could be increased later on.

               
                
     (extend-env 'count (num-val 0)
               
      
      
      ; #####################################################    
      ; #####################################################
       
       (extend-env
        'v (num-val 5)
        (extend-env
         'x (num-val 10)
         (empty-env)))))

    ;paranted
)
    ;

;;;;;;;;;;;;;;;; environment constructors and observers ;;;;;;;;;;;;;;;;

  ;; Page: 86
  (define apply-env
    (lambda (env search-sym)
      (cases environment env
        (empty-env ()
          (eopl:error 'apply-env "No binding for ~s" search-sym))
        
        (extend-env (var val saved-env)
          (if (eqv? search-sym var)
              
              ; #####################################################
              ; ###### ENTER YOUR CODE HERE, YOU MAY DELETE
              ; ###### THE CODE BELOW, IT IS PUT TO PROVIDE A RUNING
              ; ###### CODE BASELINE.
              ; ######
              ; ###### You need to check the given value, and take 
              ; ###### care of the case where the given value is a 
              ; ###### nested-procedure. If it is a nested-procedure, 
              ; ###### a proc-val with a nested-procedure should be 
              ; ###### returned. Otherwise, it should behave
              ; ###### as it normally does.
              ; #####################################################
              (if (equal? 'proc val)
              (cases proc var
                (nested-procedure (bvar id body count env)  (proc-val (nested-procedure bvar var body count env)))
                (else val)) val)
     




 
              ; #####################################################
                        
              (apply-env saved-env search-sym))
        )
        
        (extend-env-rec (p-name b-var p-body saved-env)
          (if (eqv? search-sym p-name)
            (proc-val (procedure b-var p-body env))          
            (apply-env saved-env search-sym)))
        
        ; #####################################################
        ; ###### ENTER YOUR CODE HERE
        ; ###### You need to add the variant extend-env-rec-nested, 
        ; ###### for the nested procedures.
        ; #####################################################


        (extend-env-rec-nested (id bvar body count saved-env)


 (if (eqv? search-sym id)
            (proc-val (nested-procedure bvar id body count env))          
            (apply-env saved-env search-sym))
                               )                         
  
     



        ; #####################################################
      )
    )
  )
)