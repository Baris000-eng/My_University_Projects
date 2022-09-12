--------------------------------------------------------------------------------
-- Company: 
-- Engineer:
--
-- Create Date:   21:30:24 03/30/2021
-- Design Name:   
-- Module Name:   C:/Test/BitAluFor1Bit/BitAlu1BitSimulation.vhd
-- Project Name:  BitAluFor1Bit
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: BitAlufor1Bit
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
 
ENTITY BitAlu1BitSimulation IS
END BitAlu1BitSimulation;
 
ARCHITECTURE behavior OF BitAlu1BitSimulation IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT BitAlufor1Bit
    PORT(
         M : IN  std_logic;
         S1 : IN  std_logic;
         S0 : IN  std_logic;
         A : IN  std_logic;
         B : IN  std_logic;
         C : IN  std_logic;
         F : OUT  std_logic;
         Cout : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal M : std_logic := '0';
   signal S1 : std_logic := '0';
   signal S0 : std_logic := '0';
   signal A : std_logic:= '0';
   signal B : std_logic:='0';
   signal C : std_logic:='0';

 	--Outputs
   signal F : std_logic;
   signal Cout : std_logic;
   -- No clocks detected in port list. Replace <clock> below with 
   -- appropriate port name 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: BitAlufor1Bit PORT MAP (
          M => M,
          S1 => S1,
          S0 => S0,
          A => A,
          B => B,
          C => C,
          F => F,
          Cout => Cout
        );
   -- Stimulus process
   stim_proc: process
   begin		
	  M<='0';
		S1<='1';
		S0<='0';
		A<='1';
		B<='1';
      -- hold reset state for 100 ns.
      wait for 100 ns;	
		M<='0';
		S1<='0';
		S0<='0';
		A<='0';
		B<='1';
      wait for 100 ns;
		M<='0';
		S1<='1';
		S0<='1';
		A<='0';
		B<='0';
      -- insert stimulus here 

      wait;
   end process;

END;



