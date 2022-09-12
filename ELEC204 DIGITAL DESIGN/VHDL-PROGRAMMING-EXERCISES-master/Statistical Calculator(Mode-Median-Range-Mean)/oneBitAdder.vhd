library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity oneBitAdder is
    Port ( oneBitCin : in  STD_LOGIC;
	        oneBitNum1 : in  STD_LOGIC;
           oneBitNum2 : in  STD_LOGIC;  
			  oneBitResult : out  STD_LOGIC;
           oneBitCout : out  STD_LOGIC);
end oneBitAdder;

architecture Behavioral of oneBitAdder is
	SIGNAL num1ToSum:STD_LOGIC;
	SIGNAL num2ToSum:STD_LOGIC;
	SIGNAL cToSum:STD_LOGIC;
	

begin
		num1ToSum<= '0' when oneBitNum1='U' else
						oneBitNum1;
		
		
		num2ToSum<= '0' when oneBitNum2='U' else
						oneBitNum2;
		
		cToSum<= '0' when oneBitCin='U' else
						oneBitCin;
						
		oneBitResult <= (num1ToSum xor num2ToSum) xor cToSum;
		oneBitCout <=  (num1ToSum and num2ToSum) or (num1ToSum and cToSum) or (num2ToSum and cToSum);
	
end Behavioral;

