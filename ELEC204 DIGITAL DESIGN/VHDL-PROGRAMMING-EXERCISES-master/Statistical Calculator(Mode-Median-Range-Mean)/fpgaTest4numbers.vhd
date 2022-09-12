
LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
USE ieee.numeric_std.ALL;
 
ENTITY fpgaTest4numbers IS
END fpgaTest4numbers;
 
ARCHITECTURE behavior OF fpgaTest4numbers IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT Main
    PORT(
         clk : IN  std_logic;
         switch0 : IN  std_logic;
         switch1 : IN  std_logic;
         switch2 : IN  std_logic;
         switch3 : IN  std_logic;
         switch8 : IN  std_logic;
         switch9 : IN  std_logic;
         num1EnteredSwitch : IN  std_logic;
         num2EnteredSwitch : IN  std_logic;
         num3EnteredSwitch : IN  std_logic;
         num4EnteredSwitch : IN  std_logic;
         result : OUT  std_logic_vector(3 downto 0)
        );
    END COMPONENT;
    

   --Inputs
   signal clk : std_logic := '0';
   signal switch0 : std_logic := '0';
   signal switch1 : std_logic := '0';
   signal switch2 : std_logic := '0';
   signal switch3 : std_logic := '0';
   signal switch8 : std_logic := '0';
   signal switch9 : std_logic := '0';
   signal num1EnteredSwitch : std_logic := '0';
   signal num2EnteredSwitch : std_logic := '0';
   signal num3EnteredSwitch : std_logic := '0';
   signal num4EnteredSwitch : std_logic := '0';

 	--Outputs
   signal result : std_logic_vector(3 downto 0);

   -- Clock period definitions
   constant clk_period : time := 1 ns;
 
BEGIN
 
	-- Instantiate the Unit Under Test (UUT)
   uut: Main PORT MAP (
          clk => clk,
          switch0 => switch0,
          switch1 => switch1,
          switch2 => switch2,
          switch3 => switch3,
          switch8 => switch8,
          switch9 => switch9,
          num1EnteredSwitch => num1EnteredSwitch,
          num2EnteredSwitch => num2EnteredSwitch,
          num3EnteredSwitch => num3EnteredSwitch,
          num4EnteredSwitch => num4EnteredSwitch,
          result => result
        );

   -- Clock process definitions
   clk_process :process
   begin
		clk <= '0';
		wait for clk_period/2;
		clk <= '1';
		wait for clk_period/2;
   end process;
 

   -- Stimulus process
   stim_proc: process
   begin		
      -- hold reset state for 100 ns.
      wait for 10 ns;
		--num1 = 0010  =  2
		switch3<='0';wait for 10 ns;switch2<='0';wait for 10 ns;switch1<='1';wait for 10 ns;switch0<='0';wait for 10 ns;
		num1EnteredSwitch<='1';wait for 10 ns;
		--num2 = 1110  =  14
		switch3<='1';wait for 10 ns;switch2<='1';wait for 10 ns;switch1<='1';wait for 10 ns;switch0<='0';wait for 10 ns;
		num2EnteredSwitch<='1';wait for 10 ns;
		--num3 = 0010  =  2
		switch3<='0';wait for 10 ns;switch2<='0';wait for 10 ns;switch1<='1';wait for 10 ns;switch0<='0';wait for 10 ns;
		num3EnteredSwitch<='1';wait for 10 ns;
		--num4 = 1010  =  10
		switch3<='1';wait for 10 ns;switch2<='0';wait for 10 ns;switch1<='1';wait for 10 ns;switch0<='0';wait for 10 ns;
		num4EnteredSwitch<='1';wait for 10 ns;

		--{2,14,2,10} -> mean=7, mode=2, median=6, range=12
    
		
		switch9<='0';switch8<='1';wait for 10 ns;
		switch9<='1';switch8<='0';wait for 10 ns;
		switch9<='1';switch8<='1';wait for 10 ns;
      

      wait;
   end process;

END;
