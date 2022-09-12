library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity sum2Medians is
	  Port (
	  num1 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
	  num2 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
     result : out  STD_LOGIC_VECTOR (4 DOWNTO 0)
	  );
	
end sum2Medians;

architecture Behavioral of sum2Medians is
	component oneBitAdder
		Port ( oneBitCin : in  STD_LOGIC;
		     oneBitNum1 : in  STD_LOGIC;
           oneBitNum2 : in  STD_LOGIC;
			  oneBitResult : out  STD_LOGIC;
           oneBitCout : out  STD_LOGIC);
	end component;

	SIGNAL C : STD_LOGIC_VECTOR (11 DOWNTO 1);
begin
	
	zerothBit:  oneBitAdder port map ('0' ,num1(0),num2(0),result(0),C(1));
	firstBit:   oneBitAdder port map (C(1),num1(1),num2(1),result(1),C(2));
	secondBit:  oneBitAdder port map (C(2),num1(2),num2(2),result(2),C(3)); 
	thirdBit:   oneBitAdder port map (C(3),num1(3),num2(3),result(3),C(4));
	forthBit:   oneBitAdder port map (C(4),'0','0',result(4),C(5));
	

end Behavioral;

