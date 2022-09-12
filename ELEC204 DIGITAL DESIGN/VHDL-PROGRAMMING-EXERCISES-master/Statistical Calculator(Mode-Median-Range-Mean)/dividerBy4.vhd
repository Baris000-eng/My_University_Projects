library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity dividerBy4 is
    Port ( num : in  STD_LOGIC_VECTOR(6 downto 0);
           result : inout  STD_LOGIC_VECTOR(3 downto 0));
end dividerBy4;

architecture Behavioral of dividerBy4 is

begin
	result(0) <= num(2);
	result(1) <= num(3);
	result(2) <= num(4);
	result(3) <= num(5);
	
end Behavioral;