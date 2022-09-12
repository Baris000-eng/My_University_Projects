--------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
--
-- Create Date:   17:31:20 03/18/2021
-- Design Name:   
-- Module Name:   C:/Test/SComparator/SComparatorSim.vhd
-- Project Name:  SComparator
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: SComparator
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
 
ENTITY SComparatorSim IS
END SComparatorSim;
 
ARCHITECTURE behavior OF SComparatorSim IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT SComparator
    PORT(
         C : IN  std_logic_vector(3 downto 0);
         D : IN  std_logic_vector(3 downto 0);
         GCD : OUT  std_logic;
         GDC : OUT  std_logic;
         E : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal C : std_logic_vector(3 downto 0) := (others => '0');
   signal D : std_logic_vector(3 downto 0) := (others => '0');

 	--Outputs
   signal GCD : std_logic;
   signal GDC : std_logic;
   signal E : std_logic;
   -- No clocks detected in port list. Replace <clock> below with 
   -- appropriate port name 
 
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: SComparator PORT MAP (
          C => C,
          D => D,
          GCD => GCD,
          GDC => GDC,
          E => E
        );

 

   -- Stimulus process
    stim_proc: process
   begin		
		C<="0010";
		D<="1001";
      wait for 20 ns;	
		C<="1001";  
      D<="0010";  
      wait for 20 ns;
      C<="1111";  
      D<="0000"; 
      -- insert stimulus here 

      wait;
   end process;

END;


