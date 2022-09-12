
----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    21:29:19 03/30/2021 
-- Design Name: 
-- Module Name:    BitAlufor1Bit - Behavioral 
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

entity BitAlufor1Bit is
    Port ( M : in  STD_LOGIC;
           S1 : in  STD_LOGIC;
           S0 : in  STD_LOGIC;
           A : in  STD_LOGIC;
           B : in  STD_LOGIC;
           C : in  STD_LOGIC;
           F : out  STD_LOGIC;
           Cout : out  STD_LOGIC);
end BitAlufor1Bit;

architecture Behavioral of BitAluFor1Bit is
signal sg1: STD_LOGIC;
signal sg2: STD_LOGIC;
signal sg3: STD_LOGIC;
signal sg4: STD_LOGIC;
signal sg5: STD_LOGIC;
signal sg6: STD_LOGIC;
signal sg7: STD_LOGIC;
signal sg8: STD_LOGIC;
signal sg9: STD_LOGIC;
signal sg10: STD_LOGIC;
signal sg11: STD_LOGIC;
signal sg12: STD_LOGIC;
signal mnt: STD_LOGIC;
signal s1nt: STD_LOGIC;
signal s0nt: STD_LOGIC;
signal cnt: STD_LOGIC;
signal ant: STD_LOGIC;
signal bnt: STD_LOGIC;


begin
mnt<=(not M);
s1nt<=(not S1);
s0nt<=(not S0);
cnt<=(not C);
ant<=(not A);
bnt<=(not B);
sg1<=((mnt) and (s1nt) and (s0nt)) and ((A and B));
sg2<=((mnt) and (s1nt) and (S0)) and (A or B);
sg3<=((mnt) and (S1) and (s0nt)) and (A xor B);
sg4<=((mnt) and (S1) and (S0)) and (A xnor B);
sg5<=((M) and (s1nt) and (s0nt) and (cnt)) and (A);
sg6<=((M) and (s1nt) and (s0nt) and (C)) and (A xor '1');
sg7<=((M) and (s1nt) and (S0) and (cnt)) and (A xor B);
sg8<=((M) and (s1nt) and (S0) and (C)) and ((A xor B) xor '1');
sg9<=((M) and (S1) and (s0nt) and (cnt)) and (A xor (bnt));
sg10<=((M) and (S1) and (s0nt) and (C)) and (A xor (bnt) xor '1');
sg11<=((M) and (S1) and (S0) and (cnt)) and (B xor (ant));
sg12<=((M) and (S1) and (S0) and (C)) and (B xor (ant) xor '1');
Cout<=((M) and (s1nt) and (s0nt) and (cnt) and '0')or((M) and (s1nt) and (s0nt) and C and A)or((M) and (s1nt) and (S0) and (cnt) and (A and B))or((M) and (s1nt) and S0 and C and (A or B))or(M and S1 and (s0nt) and (not C) and (A and (bnt))) or (M and S1 and (s0nt) and C and (A or (bnt))) or (M and S1 and S0 and (cnt) and (B and (ant))) or (M and S1 and S0 and C and (B or (ant)));
F<=sg1 or sg2 or sg3 or sg4 or sg5 or sg6 or sg7 or sg8 or sg9 or sg10 or sg11 or sg12;


end Behavioral;

