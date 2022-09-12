library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity subtractor4bit4bit is
	  Port (
	  num1 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
	  num2 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
     result : out  STD_LOGIC_VECTOR (3 DOWNTO 0)
	  );
	
end subtractor4bit4bit;

architecture Behavioral of subtractor4bit4bit is
	component oneBitAdder
		Port ( oneBitCin : in  STD_LOGIC;
		     oneBitNum1 : in  STD_LOGIC;
           oneBitNum2 : in  STD_LOGIC;
			  oneBitResult : out  STD_LOGIC;
           oneBitCout : out  STD_LOGIC);
	end component;

	SIGNAL C : STD_LOGIC_VECTOR (4 DOWNTO 1);
	SIGNAL complementOfNum2: STD_LOGIC_VECTOR (3 DOWNTO 0);
begin
	
	complementOfNum2 <= not num2;
	zerothBit:  oneBitAdder port map ('0' ,num1(0),complementOfNum2(0),result(0),C(1));
	firstBit:   oneBitAdder port map (C(1),num1(1),complementOfNum2(1),result(1),C(2));
	secondBit:  oneBitAdder port map (C(2),num1(2),complementOfNum2(2),result(2),C(3)); 
	thirdBit:   oneBitAdder port map (C(3),num1(3),complementOfNum2(3),result(3),C(4));
	
	

end Behavioral;

