--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   13:57:14 04/01/2021
-- Design Name:   
-- Module Name:   C:/Test/BitAlufor1Bit/BitAlu4BitSimulation.vhd
-- Project Name:  BitAlufor1Bit
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: BitAlufor4Bit
-- 
-- Dependencies:
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
--
-- Notes: 
-- This testbench has been automatically generated using types std_logic and
-- std_logic_vector for the ports of the unit under test.  Xilinx recommends
-- that these types always be used for the top-level I/O of a design in order
-- to guarantee that the testbench will bind correctly to the post-implementation 
-- simulation model.
--------------------------------------------------------------------------------
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY BitAlu4BitSimulation IS
END BitAlu4BitSimulation;
 
ARCHITECTURE behavior OF BitAlu4BitSimulation IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
	 
 
    COMPONENT BitAlufor4Bit
    PORT(
         K : IN  std_logic;
         S1in : IN  std_logic;
         S0in : IN  std_logic;
         Ain : IN  std_logic_vector(3 downto 0);
         Bin : IN  std_logic_vector(3 downto 0);
         Cin : IN  std_logic;
         Fo : OUT  std_logic_vector(3 downto 0);
         Co : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal K : std_logic := '0';
   signal S1in : std_logic := '0';
   signal S0in : std_logic := '0';
   signal Ain : std_logic_vector(3 downto 0) := (others => '0');
   signal Bin : std_logic_vector(3 downto 0) := (others => '0');
   signal Cin : std_logic := '0';

 	--Outputs
   signal Fo : std_logic_vector(3 downto 0);
   signal Co : std_logic;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: BitAlufor4Bit PORT MAP (
          K => K,
          S1in => S1in,
          S0in => S0in,
          Ain => Ain,
          Bin => Bin,
          Cin => Cin,
          Fo => Fo,
          Co => Co
        );
   stim_proc: process
   begin		
	              K<='0';
		S1in<='0';
		S0in<='0';
		Ain<="1111";
		Bin<="0000";
      -- hold reset state for 100 ns.
      wait for 75 ns;	
		K<='0';
		S1in<='0';
		S0in<='1';
		Ain<="1101";
		Bin<="0010";
		wait for 75 ns;
		K<='0';
		S1in<='1';
		S0in<='0';
		Ain<="1111";
		Bin<="0110";
wait for 100 ns;
		K<='0';
		S1in<='1';
		S0in<='1';
		Ain<="1111";
		Bin<="0111";
      wait for 75 ns;
		K<='1';
		S1in<='0';
		S0in<='0';
		Ain<="1111";
		Bin<="0011";
		Cin<='0';
	  wait for 75 ns;
		K<='1';
		S1in<='0';
		S0in<='0';
		Ain<="1111";
		Bin<="0001";
		Cin<='1';
		wait for 75 ns;
		K<='1';
		S1in<='0';
		S0in<='1';
		Ain<="1010";
		Bin<="0001";
		Cin<='0';
		wait for 75 ns;
		K<='1';
		S1in<='0';
		S0in<='1';
		Ain<="0000";
		Bin<="0001";
		Cin<='1';
		wait for 75 ns;
		K<='1';
		S1in<='1';
		S0in<='0';
		Ain<="1001";
		Bin<="0000";
		Cin<='0';
		wait for 75 ns;
		K<='1';
		S1in<='1';
		S0in<='0';
		Ain<="0011";
		Bin<="0000";
		Cin<='0';
		wait for 75 ns;
		K<='1';
		S1in<='1';
		S0in<='0';
		Ain<="0111";
		Bin<="1000";
		Cin<='1';
		wait for 75 ns;
		K<='1';
		S1in<='1';
		S0in<='1';
		Ain<="1100";
		Bin<="1000";
		Cin<='0';
		wait for 75 ns;
		K<='1';
		S1in<='1';
		S0in<='1';
		Ain<="1111";
		Bin<="1000";
		Cin<='1';
		wait;
   end process;

END;
