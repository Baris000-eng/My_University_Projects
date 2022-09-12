(module ps3 mzscheme
  
  ;;;;;;;;;;;;;;;;;;; PROBLEMS ;;;;;;;;;;;;;;;;;;;;
  ;; PROBLEM 1 Part A | Write your answer below here as a comment
  
  ; Unary Representation:
  ; If a natural number is equal to 0, it is represented by '() in the unary representation. In other words, it is represented by 0 #t's in the unary representation.
  ; If a natural number is bigger than 0 and equal to natNum, it is represented by natNum #t's in the unary representation.
  ;Unary Representation Examples:
  ; In the unary representation, 3(in decimal) is represented by '(#t #t #t)
  ; In the unary representation, 4(in decimal) is represented by '(#t #t #t #t)
  ; In the unary representation, 5(in decimal) is represented by '(#t #t #t #t #t)
  ; In the unary representation, 6(in decimal) is represented by '(#t #t #t #t #t #t)
  
  ; Bignum Representation:
  ; The representations of the natural numbers are in base N in the Bignum representation (Here N>=1)
  ; The Bignum Representation is a list representation which includes the numbers in the range 0<=num<N.
  ; In the above inequality (0<=num<N), num represents the numbers included in the Bignum representation of a natural number.
  ; Bignum Representation Examples:
  ; Let's say N= 10 (base=N and N=10)
  ; In Bignum representation, then 100(in decimal) is (0 0 1) (base= N and N=10 , 0*(10^0)+0*(10^1)+1*(10^2)= 0*1+0*10+1*100= 0+0+100= 100)
  ; In Bignum representation, then 102(in decimal) is (2 0 1) (base= N and N=10 , 2*(10^0)+0*(10^1)+1*(10^2)= 2+0+100=102)
  ; In Bignum representation, then 213(in decimal) is (3 1 2) (base= N and N=10 , 3*(10^0)+1*(10^1)+2*(10^2)= 3+10+200=213)
  ; In Bignum representation, then 395(in decimal) is (5 9 3) (base= N and N=10 , 5*(10^0)+9*(10^1)+3*(10^2)= 5+9*10+3*100= 5+90+300= 395)
  ;
  ;  Let's say N= 7
  ;  Then, 99 is (1 0 2) in the Bignum representation. (base=N and N=7    ,   1*(7^0)+0*(7^1)+2*(7^2)= 1*1+0*7+2*49= 1+0+98=99)
  ;  Then, 50 is (1 0 1) in the Bignum representation. (base=N and N=7    ,   1*(7^0)+0*(7^1)+1*(7^2)= 1*1+0*7+1*49= 1+49= 50)
  ;
  ; In the Bignum representation of a natural number, the left-most number is the least significant digit and
  ; the right-most number is the most significant digit.
  
  ;; PROBLEM 1 Part B
  ;; Unary Representation | We added a -u suffix so that both Unary and BigNum can be tested at once.

  (define create-u (lambda (k)
      (if (= k 0)
          '()
          (cons '#t (create-u (- k 1))))))

  (define is-zero-u?
    (lambda(z) (null? z)))

  (define predecessor-u
    (lambda(c)
      (if(null? c)
         'error-only-positive-numbers
          (cdr c))))

  ;; BigNum Representation | We added a -b suffix so that both Unary and BigNum can be tested at once.
(define create-b
    (lambda (numb baseNum)
        (if(eq? numb 0)
           '()
            (cons (custom_remainder_impl numb baseNum) (create-b (quotient numb baseNum) baseNum)))))
          
(define custom_remainder_impl(lambda(initialNum secondNum)
      (- initialNum (* (floor(/ initialNum secondNum)) secondNum))))


(define is-zero-b?
  (lambda (my_number)
    (eq? my_number '())))
    


 (define predecessor-b
  (lambda (myNumber baseNumber)
      (if (null? myNumber)
          'error-only-positive-numbers
      (if (equal? 0 (car myNumber))
          (cons (- baseNumber 1) (predecessor-b (cdr myNumber) baseNumber))
          (if (and (null? (cdr myNumber)) (equal? 1 (car myNumber)))
              (cons 0 '())
              (cons (- (car myNumber) 1) (cdr myNumber)))))))

  ;; PROBLEM 2 Part A
  (define count-free-occurrences
    (lambda (sym exp) ()))

  ;; PROBLEM 2 Part B (optional)
  (define product
    (lambda (sos1 sos2)()))
  
   ;;;;;;;;;;;;;;;;;;; TESTS ;;;;;;;;;;;;;;;;;;;;;;;;
  ;;; Don't worry about the below function, we included it to test your implemented functions and display the result in the console
  ;; As you implement your functions you can Run (the button on the top right corner) to test your implementations
  (define-syntax equal??
    (syntax-rules ()
      ((_ test-exp correct-ans)
       (let ((observed-ans test-exp))
         (if (not (equal? observed-ans correct-ans))
           (printf "Oops! ~s returned ~s, should have returned ~s~%" 'test-exp observed-ans correct-ans)
           (printf "Correct! ~s => ~s~%" 'test-exp correct-ans))))))
  
  ;;; If you don't implement the functions in order and want to test as you go, you can comment out the corresponding tests,
  ;;; otherwise, DrRacket will raise errors.
  ;; PROBLEM 1 TESTS
  ;;; For unary representation
  (display "Unary Tests\n")
  (equal?? (create-u 4) '(#t #t #t #t)) ; should return '(#t #t #t #t)
  (equal?? (is-zero-u? '(#t #t #t)) #f) ; should return #f
  (equal?? (is-zero-u? '()) #t) ; should return #t
  (equal?? (predecessor-u '(#t #t #t)) '(#t #t)) ; should return '(#t #t)
  (equal?? (predecessor-u '()) 'error-only-positive-numbers)
  (newline)

  ;;; For BigNum representation
  (display "\nBigNum Tests\n")
  (equal?? (create-b 15 4) '(3 3)) ; should return '(3 3)
  (equal?? (is-zero-b? (create-b 0 4)) #t) ; should return #t
  (equal?? (is-zero-b? (create-b 5 4)) #f) ; should return #f
  (equal?? (predecessor-b (create-b 31 4) 4) '(2 3 1)) ; should return '(2 3 1)
  (equal?? (predecessor-b (create-b 64 4) 4) '(3 3 3 0)) ; should return '(3 3 3 0)
  (equal?? (predecessor-b (create-b 0 4) 4) 'error-only-positive-numbers) ; should return error

  (newline)

  ;; PROBLEM 2 Part A TESTS
  (display "\nCount Free Occurences Tests\n")
  (equal?? (count-free-occurrences 'x 'x) 1) ;1
  (equal?? (count-free-occurrences 'x 'y) 0) ;0
  (equal?? (count-free-occurrences 'x '(lambda (x) (x y))) 0) ;0
  (equal?? (count-free-occurrences 'x '(lambda (y) (x x))) 2) ;2
  (equal?? (count-free-occurrences 'x '((lambda (xx) x) (x y))) 2) ;2
  (equal?? (count-free-occurrences 'x '((lambda (x) (y x)) (lambda (y) (x (lambda (z) x))))) 2) ;2

  ;; PROBLEM 2 Part B TESTS (Optional)
  (display "\nCartesian Product Tests\n")
  (equal?? (product '(x y) '(a b)) '((x a) (x b) (y a) (y b)))
  (equal?? (product '() '(a b)) '())
  
)