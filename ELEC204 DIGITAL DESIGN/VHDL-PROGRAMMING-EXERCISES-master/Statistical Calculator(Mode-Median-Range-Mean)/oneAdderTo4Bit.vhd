library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity oneAdderTo4Bit is
	  Port (
	  num1 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
     result : out  STD_LOGIC_VECTOR (3 DOWNTO 0)
	  );
	
end oneAdderTo4Bit;

architecture Behavioral of oneAdderTo4Bit is
	component oneBitAdder
		Port ( oneBitCin : in  STD_LOGIC;
		     oneBitNum1 : in  STD_LOGIC;
           oneBitNum2 : in  STD_LOGIC;
			  oneBitResult : out  STD_LOGIC;
           oneBitCout : out  STD_LOGIC);
	end component;

	SIGNAL C : STD_LOGIC_VECTOR (4 DOWNTO 1);
begin
	
	zerothBit:  oneBitAdder port map ('0' ,num1(0),'1',result(0),C(1));
	firstBit:   oneBitAdder port map (C(1),num1(1),'0',result(1),C(2));
	secondBit:  oneBitAdder port map (C(2),num1(2),'0',result(2),C(3)); 
	thirdBit:   oneBitAdder port map (C(3),num1(3),'0',result(3),C(4));
	

end Behavioral;

