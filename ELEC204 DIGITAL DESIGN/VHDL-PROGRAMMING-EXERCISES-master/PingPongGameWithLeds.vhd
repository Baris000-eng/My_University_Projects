
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
library UNISIM;
use UNISIM.VComponents.all;

entity Lab4Code is
    Port ( PushBt1 : in  STD_LOGIC;
           PushBt2 : in  STD_LOGIC;
           MCLK : in  STD_LOGIC;
           LedDisplay : out  STD_LOGIC_VECTOR (9 downto 0);
           SevenSegment1 : out  STD_LOGIC_VECTOR (6 downto 0);
          SevenSegment2: out STD_LOGIC_VECTOR(6 downto 0));
end Lab4Code;

architecture Behavioral of Lab4Code is

signal LedRegion : std_logic_vector(9 downto 0):="1000000000";

signal directionBit : STD_LOGIC:='0';

signal scoreLeft : unsigned(6 downto 0):="0000000";

signal scoreRight : unsigned(6 downto 0):="0000000";

begin
process(MCLK)
begin
if(rising_edge(MCLK)) then
    if(LedRegion="1000000000" and directionBit='0') then-- start of the shift of the left most bit which is 1.
	    if(pushBt1='1') then
		  scoreLeft<=scoreLeft+1;
		  end if;
		  LedRegion<="0100000000";
		  directionBit<='0';
	end if;
	elsif(LedRegion="0100000000" and directionBit='0') then
		  LedRegion<="0010000000";
		  directionBit<='0';
	elsif(LedRegion="0010000000" and directionBit='0') then
		  LedRegion<="0001000000";
		  directionBit<='0';
	  elsif(LedRegion="0001000000" and directionBit='0') then
		  LedRegion<="0000100000";
		  directionBit<='0';
	  elsif(LedRegion="0000100000" and directionBit='0') then
		  LedRegion<="0000010000";
		  directionBit<='0';
	  elsif(LedRegion="0000010000" and directionBit='0') then
		  LedRegion<="0000001000";
		  directionBit<='0';
	  elsif(LedRegion="0000001000" and directionBit='0') then
		  LedRegion<="0000000100";
		  directionBit<='0';
	  elsif(LedRegion="0000000100" and directionBit='0') then
		  LedRegion<="0000000010";
		  directionBit<='0';
	  elsif(LedRegion="0000000010" and directionBit='0') then
		  LedRegion<="0000000001";
			directionBit<='1';
		elsif(LedRegion="0000000001" and directionBit='1') then
		  if(pushBt2='1') then
		  scoreRight<=scoreRight+1;
		  end if;
		  LedRegion<="0000000010";	
        directionBit<='1'; 		  
		elsif(LedRegion="0000000010" and directionBit='1') then
		    LedRegion<="0000000100";
			  directionBit<='1'; 
		elsif(LedRegion="0000000100" and directionBit='1' ) then
		    LedRegion<="0000001000";
			directionBit<='1'; 
		elsif(LedRegion="0000001000" and directionBit='1') then
		    LedRegion<="0000010000";
			  directionBit<='1'; 
		elsif(LedRegion="0000010000" and directionBit='1') then
		    LedRegion<="0000100000";
			  directionBit<='1'; 
		elsif(LedRegion="0000100000"and directionBit='1') then
		    LedRegion<="0001000000"; 
			  directionBit<='1'; 
		elsif(LedRegion="0001000000"and directionBit='1') then
		    LedRegion<="0010000000";
			  directionBit<='1'; 
		elsif(LedRegion="0001000000" and directionBit='1') then
		    LedRegion<="0010000000";
			  directionBit<='1'; 
		elsif(LedRegion="0010000000" and directionBit='1') then
		    LedRegion<="0100000000";
			  directionBit<='1'; 
		elsif(LedRegion="0100000000" and directionBit='1') then
		    LedRegion<="1000000000";
			directionBit<='0';
		end if;
end process;
 LedDisplay<=LedRegion;
 SevenSegment1<=std_logic_vector(scoreLeft);
 SevenSegment2<=std_logic_vector(scoreRight);

end Behavioral;
