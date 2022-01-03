package calculator

/**
 * The Ref(name) case class represents a reference to another variable in the map namedExpressions
 * The other kinds of expressions have the obvious(literal) meaning.
 * */
enum Expr:
  case Literal(v: Double)
  case Ref(name: String)
  case Plus(a: Expr, b: Expr)
  case Minus(a: Expr, b: Expr)
  case Times(a: Expr, b: Expr)
  case Divide(a: Expr, b: Expr)

object Calculator extends CalculatorInterface {

  import Expr.*

  /**
   * This function takes as input a map from variable name to expressions of their values.
   * Since the expression is derived from the text entered by the user, it is a Signal
   *
   * @param namedExpressions
   * @return The function should return another map from the same set of variable names to their actual values, computed from their expressions
   */
  def computeValues(namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.map {
      case (name, expression) => name -> Signal(eval(expression(), namedExpressions))
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]])(using Signal.Caller): Double =
    expr match {
      case Literal(v: Double) => v
      case Ref(name: String) => eval(getReferenceExpr(name, references), references - name)
      case Plus(a: Expr, b: Expr) => eval(a, references) + eval(b, references)
      case Minus(a: Expr, b: Expr) => eval(a, references) - eval(b, references)
      case Times(a: Expr, b: Expr) => eval(a, references) * eval(b, references)
      case Divide(a: Expr, b: Expr) => eval(a, references) / eval(b, references)
    }

  /** Get the Expr for a referenced variables.
   * If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
                               references: Map[String, Signal[Expr]])(using Signal.Caller): Expr =
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
}