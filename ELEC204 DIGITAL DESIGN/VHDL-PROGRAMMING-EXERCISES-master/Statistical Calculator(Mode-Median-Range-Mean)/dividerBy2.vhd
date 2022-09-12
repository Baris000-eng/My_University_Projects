library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity dividerBy2 is
    Port ( num : in  STD_LOGIC_VECTOR(4 downto 0);
           result : inout  STD_LOGIC_VECTOR(3 downto 0));
end dividerBy2;

architecture Behavioral of dividerBy2 is

begin
	result(0) <= num(1);
	result(1) <= num(2);
	result(2) <= num(3);
	result(3) <= num(4);


end Behavioral;

