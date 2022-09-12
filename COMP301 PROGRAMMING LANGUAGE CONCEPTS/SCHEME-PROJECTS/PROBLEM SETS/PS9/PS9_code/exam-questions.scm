(module tests mzscheme
  
  (provide exam-questions)
  ;;;;;;;;;;;;;;;; tests ;;;;;;;;;;;;;;;;


  ;; Nothing here :)
  (define exam-questions
    '("let x = newref(17) in
            -(deref(x),1)"

      "let x = newref(newref(17)) in
            -(deref(deref(x)),1)"

      "let x = newref(newref(newref(17))) in
            -(deref(deref(deref(x))),1)"
      )
    )
  )
