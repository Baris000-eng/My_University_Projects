----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    13:16:25 03/18/2021 
-- Design Name: 
-- Module Name:    MComparator - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity MComparator is
    Port ( A : in  STD_LOGIC_VECTOR (3 downto 0);
           B : in  STD_LOGIC_VECTOR (3 downto 0);
           GAB : out  STD_LOGIC;
           GBA : out  STD_LOGIC;
           E : out  STD_LOGIC);
end MComparator;

architecture Behavioral of MComparator is
signal e0:std_logic;
signal e1:std_logic;
signal e2:std_logic;
signal e3:std_logic;
signal NA0: std_logic;
signal NA1: std_logic;
signal NA2: std_logic;
signal NA3: std_logic;
signal NB0: std_logic;
signal NB1: std_logic;
signal NB2: std_logic;
signal NB3: std_logic;
signal E123:std_logic;
signal E23: std_logic;
begin
NA0<= not A(0);
NA1<= not A(1);
NA2<= not A(2);
NA3<= not A(3);
NB0<= not B(0);
NB1<= not B(1);
NB2<= not B(2);
NB3<= not B(3);
e0<=(not (A(0) xor B(0)));
e1<=(not (A(1) xor B(1)));
e2<=(not (A(2) xor B(2)));
e3<=(not (A(3) xor B(3)));

E123<= e3 and e2 and e1;
E23<= e3 and e2;

E <= e0 and e1 and e2 and e3;

GAB <= (A(3) and (NB3)) or (e3 and A(2) and (NB2)) or (E23 and A(1) and (NB1)) 
or (E123 and A(0) and (NB0));

GBA <= ((NA3) and B(3)) or (e3 and (NA2) and B(2)) or (E23 and (NA1) and B(1)) 
or (E123 and (NA0) and B(0));

end Behavioral;


