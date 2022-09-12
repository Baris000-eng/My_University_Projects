----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    13:48:43 04/01/2021 
-- Design Name: 
-- Module Name:    BitAlufor4Bit - Behavioral 
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

entity BitAlufor4Bit is
    Port ( K : in  STD_LOGIC;
           S1in : in  STD_LOGIC;
           S0in : in  STD_LOGIC;
           Ain : in  STD_LOGIC_VECTOR (3 downto 0);
           Bin : in  STD_LOGIC_VECTOR (3 downto 0);
           Cin : in  STD_LOGIC;
           Fo : out  STD_LOGIC_VECTOR (3 downto 0);
           Co : out  STD_LOGIC);
end BitAlufor4Bit;

architecture Behavioral of BitAlufor4Bit is
COMPONENT BitAlufor1Bit
Port ( M : in  STD_LOGIC;
           S1 : in  STD_LOGIC;
           S0 : in  STD_LOGIC;
           A : in  STD_LOGIC;
           B : in  STD_LOGIC;
           C : in  STD_LOGIC;
           F : out  STD_LOGIC;
           Cout : out  STD_LOGIC);
END COMPONENT;

signal carry1: std_logic;
signal carry2: std_logic;
signal carry3: std_logic;


begin
a : BitAlufor1Bit port map (K,S1in,S0in,Ain(0),Bin(0),Cin,Fo(0),carry1);
b : BitAlufor1Bit port map (K,S1in,S0in,Ain(1),Bin(1),carry1,Fo(1),carry2);
c : BitAlufor1Bit port map (K,S1in,S0in,Ain(2),Bin(2),carry2,Fo(2),carry3);
d : BitAlufor1Bit port map (K,S1in,S0in,Ain(3),Bin(3),carry3,Fo(3),Co);

      

end Behavioral;
