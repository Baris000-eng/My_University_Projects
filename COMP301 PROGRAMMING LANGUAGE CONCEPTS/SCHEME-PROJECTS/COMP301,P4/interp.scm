(module interp (lib "eopl.ss" "eopl")
  
  ;; interpreter for the EXPLICIT-REFS language

  (require "drscheme-init.scm")

  (require "lang.scm")
  (require "data-structures.scm")
  (require "environments.scm")
  (require "store.scm")
  
  (provide value-of-program value-of instrument-let instrument-newref)

;;;;;;;;;;;;;;;; switches for instrument-let ;;;;;;;;;;;;;;;;

  (define instrument-let (make-parameter #f))

  ;; say (instrument-let #t) to turn instrumentation on.
  ;;     (instrument-let #f) to turn it off again.

;;;;;;;;;;;;;;;; the interpreter ;;;;;;;;;;;;;;;;

  ;; value-of-program : Program -> ExpVal
  ;; Page: 110
  (define value-of-program 
    (lambda (pgm)
      (initialize-store!)               ; new for explicit refs.
      (cases program pgm
        (a-program (exp1)
          (value-of exp1 (init-env))))))

  ;; value-of : Exp * Env -> ExpVal
  ;; Page: 113
  (define value-of
    (lambda (exp env)
      (cases expression exp

        ;\commentbox{ (value-of (const-exp \n{}) \r) = \n{}}
        (const-exp (num) (num-val num))

        ;\commentbox{ (value-of (var-exp \x{}) \r) = (apply-env \r \x{})}
        (var-exp (var) (apply-env env var))

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

        ;\commentbox{\ma{\theletspecsplit}}
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

        (newref-exp (exp1)
          (let ((v1 (value-of exp1 env)))
            (ref-val (newref v1))))

        (deref-exp (exp1)
          (let ((v1 (value-of exp1 env)))
            (let ((ref1 (expval->ref v1)))
              (deref ref1))))

        (setref-exp (exp1 exp2)
          (let ((ref (expval->ref (value-of exp1 env))))
            (let ((v2 (value-of exp2 env)))
              (begin
                (setref! ref v2)
                (num-val 23)))))

        ; #####################################################
        ; ###### ENTER YOUR CODE HERE
        ; ###### value-of cases for new expressions, remember
        ; ###### that you need to use memory functionalities. 
        ; #####################################################

        ;newarray
        (newarray-exp (length value)  ;length and value
                      (let ((v1 (expval->num (value-of length env)))  ;get the length as a int
                            (v2 (value-of value env)))                ;get the value in expval
                        (arr-val (create-array v1 v2))                ;create the array
                        ))
                           
        ;update-array
        (update-array-exp (arr index value)
                          (let ((v1 (expval->arr (value-of arr env)))  ;get array  
                                (v2 (expval->num (value-of index env))) ;get index
                                (v3 (value-of value env)))              ;get new value
                              (setref! (list-ref v1 v2) v3)))           ;set the value to the given index

        ;read-array
        (read-array-exp (arr index)
                        (let ((v1 (expval->arr (value-of arr env)))    ;get  array
                              (v2 (expval->num (value-of index env)))) ;get index
                          (deref (list-ref v1 v2))))                   ;use deref to get the value at the index

        ;print-array
      (print-array-exp (arr)
                         (let ((arr2 (expval->arr (value-of arr env))))  ;get array
                           (let ((len (length arr2)))                    ;get length
                           (letrec ((helper (lambda (helper-array index end)     ;create helper function
                                                      (if (= index end) (display "")   ;if end of the array, return empty string
                                                          (begin 
                                                            (display " ")    ;put the spaces between the array elements
                                                            (display (expval->num (deref index)))   ;display the current element in the array being iterated.
                                                            (helper helper-array (+ index 1) end) )))))  ;call the helper with index incremented by 1
                                                                (begin 
                                                                  (display "[")   ;as specified in the pdf, printing starts with [
                                                                  (helper arr2 0 len)  ;call the helper function
                                                                  (display " ]")))))) ;as specified in the pdf, printing ends with ]

        
   
        
        ;for newstack-exp
        (newstack-exp ()
                      (let ((empty-stack (create-array 1024 (num-val -1024))))  ;create an array, with length 1024 (the size can't be bigger than 1000 (but we have set it to 1024 to make it power 2)
                                                                                ;and initialize every value with -1024 
                        (begin
                          (setref! (list-ref empty-stack 0) (num-val 0))    ;the 0th element will be set to 0, because at the 0th index, we store the size
                          (arr-val empty-stack))))                          ;return the arr-val

        ;for stack-push-exp
        (stack-push-exp (stk val)
                        (let ((stack (expval->arr (value-of stk env)))      ;get stack
                              (new-val (value-of val env)))                 ;get the new value that will be pushed as expval
                                   (let ((new-index (+ 1 (expval->num (deref (list-ref stack 0)))))) ;get the index that the new value will be pushed on
                                     (begin
                                       (setref! (list-ref stack new-index) new-val)   ;set the new value at top
                                       (setref! (list-ref stack 0) (num-val new-index))))))  ;increment the size

        ;for stack-pop-exp
        (stack-pop-exp (stk)
                       (let ((stack (expval->arr (value-of stk env))))  ;get stack
                        (let ((size (deref (list-ref stack 0))))        ;get size
                         (let ((popped-element (deref (list-ref stack (expval->num size))))  ;get the popped-element, this will be returned
                               (sizeInt (expval->num size)))                                 ;get the size as an int
                                 (if (= (expval->num size) 0)
                                 (num-val -1)
                                 (begin
                                   (setref! (list-ref stack sizeInt) (num-val -1024))              ;set the last element to -1024
                                   (setref! (list-ref stack 0) (num-val (- sizeInt 1)))            ;decrement the size
                                   popped-element))))))
                           

        ;for stack-size-exp
        (stack-size-exp (stk)
                        (let ((stack (expval->arr (value-of stk env))))  ;get stack
                            (deref (list-ref stack 0))))             ;return the size by calling deref on 0th element of stack

        ;for stack-top-exp
        (stack-top-exp (stk)
                       (let ((stack (expval->arr (value-of stk env))))     ;get stack
                            (deref (list-ref stack (expval->num (deref (list-ref stack 0)))))))  ;get the size, size will point to the last element
                                                                                                 ;deref to the last element


        ;for empty-stack?-exp
        (empty-stack?-exp (stk)
                           (let ((stack (expval->arr (value-of stk env))))   ;get stack
                           (bool-val (equal? (deref (list-ref stack 0)) (num-val 0))))) ;get the size which is equal to deref of 0th element in the stack,
                                                                                        ;return bool-val of if (check size equal 0)

        ;for print-stack-exp
         (print-stack-exp (stack-exp)
                          (let ((arr (expval->arr (value-of stack-exp env))))  ;turn stack into an arr-val
                            (let ((limit (deref (list-ref (expval->arr (value-of stack-exp env)) 0))))   ;get the upper limit (size)                 
                              (letrec ((print-stack-exp-helper ;create a helper function
                                        (lambda (stack-helper index)   
                                          (if
                                              (= index (expval->num limit) )  ;if index equals limit, display the last element
                                              (display (expval->num (deref (list-ref stack-helper index))))  ;display the last element
                                              (begin                                                        
                                                (display (expval->num (deref (list-ref stack-helper index))))  ;otherwise, display current element
                                                (display " ")                                                  ;put space 
                                                (print-stack-exp-helper stack-helper (+ index 1)))))))         ;call the helper function again with index incremented by one
                                  (print-stack-exp-helper arr 1)  ;call the helper function                         
                                 ))))

   

         (array-comprehension-exp (body var arr-exp)
                           (let ((v1 (expval->arr (value-of arr-exp env))))  ;get array
                                 (let ((len (length v1)))                    ;get length 
                                   (let ((a (expval->proc (proc-val (procedure var body env)))))    ;create proc      
                                      (letrec ((helper (lambda (array2 index end) (if (= index end) (arr-val array2)  ;create helper function, base case is index equal to end, which means end of the array
                                           (begin
                                           (setref! (list-ref array2 index) (apply-procedure a (deref (list-ref array2 index))))   ;set the current iterated index to new value (apply proc to current value)                                                    
                                           (helper array2 (+ 1 index) end))))))  ;call helper function recursively with index incremented by one
                                                 (begin
                                                   (helper v1 0 len)))))))  ;call the helper function

                              

        
        ; #####################################################
        )))



  ; ###### YOU CAN WRITE HELPER FUNCTIONS HERE


  ;; apply-procedure : Proc * ExpVal -> ExpVal
  ;; 
  ;; uninstrumented version
  ;;   (define apply-procedure
  ;;    (lambda (proc1 arg)
  ;;      (cases proc proc1
  ;;        (procedure (bvar body saved-env)
  ;;          (value-of body (extend-env bvar arg saved-env))))))

  ;; instrumented version
  (define apply-procedure
    (lambda (proc1 arg)
      (cases proc proc1
        (procedure (var body saved-env)
	  (let ((r arg))
	    (let ((new-env (extend-env var r saved-env)))
	      (when (instrument-let)
		(begin
		  (eopl:printf
		    "entering body of proc ~s with env =~%"
		    var)
		  (pretty-print (env->list new-env))
                  (eopl:printf "store =~%")
                  (pretty-print (store->readable (get-store-as-list)))
                  (eopl:printf "~%")))
              (value-of body new-env)))))))


  ;; store->readable : Listof(List(Ref,Expval)) 
  ;;                    -> Listof(List(Ref,Something-Readable))
  (define store->readable
    (lambda (l)
      (map
        (lambda (p)
          (cons
            (car p)
            (expval->printable (cadr p))))
        l)))
 
  )
  


  
