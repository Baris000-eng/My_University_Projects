-- Company: 
-- Engineer: 
-- 
-- Create Date:    13:16:25 03/18/2021 
-- Design Name: 
-- Module Name:    SComparator - Behavioral 
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

entity SComparator is
    Port ( C : in  STD_LOGIC_VECTOR (3 downto 0);
           D : in  STD_LOGIC_VECTOR (3 downto 0);
           GCD : out  STD_LOGIC;
           GDC : out  STD_LOGIC;
           E : out  STD_LOGIC);
end SComparator;

architecture Behavioral of SComparator is
signal e0:std_logic;
signal e1:std_logic;
signal e2:std_logic;
signal e3:std_logic;
signal NC0: std_logic;
signal NC1: std_logic;
signal NC2: std_logic;
signal NC3: std_logic;
signal ND0: std_logic;
signal ND1: std_logic;
signal ND2: std_logic;
signal ND3: std_logic;
signal E123:std_logic;
signal E23: std_logic;

begin
NC0<= not C(0);
NC1<= not C(1);
NC2<= not C(2);
NC3<= not C(3);
ND0<= not D(0);
ND1<= not D(1);
ND2<= not D(2);
ND3<= not D(3);
e0<=(not (C(0) xor D(0)));
e1<=(not (C(1) xor D(1)));
e2<=(not (C(2) xor D(2)));
e3<=(not ((NC3) xor (ND3)));

E123<= e3 and e2 and e1;
E23<= e3 and e2;

E <= e0 and e1 and e2 and e3;

GCD <= ((NC3) and (D(3))) or (e3 and C(2) and (ND2)) or (E23 and C(1) and (ND1)) 
or (E123 and C(0) and (ND0));

GDC <= ((C(3)) and (ND3)) or (e3 and (NC2) and D(2)) or (E23 and (NC1) and D(1)) 
or (E123 and (NC0) and D(0));

end Behavioral;


