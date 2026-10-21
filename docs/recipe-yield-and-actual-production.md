# Optional recipe yield and measured production

- Recipe formulation has a nullable reference yield. NULL means the finished output quantity is unknown.
- If no reference yield is available, production requires an explicit complete list of actual consumption for ingredients and packaging.
- With reference yield, old production clients can still omit consumed items; input amounts are scaled from the formula.
- Explicit consumption always overrides the expected formula.
- Both simulations return reliable ingredient estimates; without yield, input-based simulation returns `quantity: null`, never fabricated finished pots.
- Real production transactions validate stock and register both consumption and finished units.
- Initial physical stock and demo-data cleanup are deliberately outside this feature. Apply migration V7 through normal backend deployment before deploying the updated frontend.
