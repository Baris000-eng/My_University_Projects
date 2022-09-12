--------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
--
-- Create Date:   15:12:43 03/18/2021
-- Design Name:   
-- Module Name:   C:/Test/MComparator/MComparatorSim.vhd
-- Project Name:  MComparator
-- Target Device:  
-- Tool versions:  
-- Description:   
-- 
-- VHDL Test Bench Created by ISE for module: MComparator
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
 
ENTITY MComparatorSim IS
END MComparatorSim;
 
ARCHITECTURE behavior OF MComparatorSim IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT MComparator
    PORT(
         A : IN  std_logic_vector(3 downto 0);
         B : IN  std_logic_vector(3 downto 0);
         GAB : OUT  std_logic;
         GBA : OUT  std_logic;
         E : OUT  std_logic
        );
    END COMPONENT;
    

   --Inputs
   signal A : std_logic_vector(3 downto 0) := (others => '0');
   signal B : std_logic_vector(3 downto 0) := (others => '0');

 	--Outputs
   signal GAB : std_logic;
   signal GBA : std_logic;
   signal E : std_logic;
   -- No clocks detected in port list. Replace <clock> below with 
   -- appropriate port name 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: MComparator PORT MAP (
          A => A,
          B => B,
          GAB => GAB,
          GBA => GBA,
          E => E
        );
   -- Stimulus process
   stim_proc: process
   begin		
		A<="0010";
		B<="1001";
      wait for 20 ns;	
		A<="1001";  
              B<="0010";  
      wait for 20 ns;
      A<="0101";  
      B<="0101"; 
      -- insert stimulus here 

      wait;
   end process;

END;


