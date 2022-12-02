(define-syntax-rules pattern template)


;;as a running example 
;;consider the swap macro

(define-syntax-rules (swap x y)
    (let ([tmp x])
        (set! x y)
        (set! y tmp)))

;;the define-sytax-rule form binds a marco that matches a single pattern
;;the pattern must always start with an open parentheis followed by an identifier
;;which is swap in this case

;;after the initial identifier
;;other identifier are marco pattern varibles
;;that can match anything in a use of the macro

;;thus this macro matches the form (swap form1 form2) for any form1 and form2

;;after the pattern in define-syntax-rule is the tamplate
;;the tamplate is used in place of a form that matches the pattern

;;except that each instance of a pattern varible in the template is replaced with
;;the part of the macro use the pattern varible matched for example in

(swap first last)

;;the pattern varible x matchs first and y match last 
;;so the expansion is

(let ([temp first])
    (set! first last)
    (set! last tmp))


;;define-syntax and syntax-rules

(define-syntax id
    (syntax-rules (literal-id ...)))



























