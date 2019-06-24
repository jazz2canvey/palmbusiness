package nutan.tech.models;

public class ExpensesModel {

	int expense_id, bank_payment_mode;
	double amount;
	String enterprise_id, entry_date, expense_name, expense_head, description;
	boolean payed_via_cash, payed_via_bank;

	
	public ExpensesModel() {
		
	}
	
	public ExpensesModel(int expense_id, int bank_payment_mode, double amount, String enterprise_id, String entry_date, String expense_name, String expense_head, String description, boolean payed_via_cash, boolean payed_via_bank) {

		
		this.expense_id = expense_id;
		this.bank_payment_mode = bank_payment_mode;
		
		this.amount = amount;
		
		this.enterprise_id = enterprise_id;
		this.entry_date = entry_date;
		this.expense_name = expense_name;
		this.expense_head = expense_head;
		this.description = description;
		
		this.payed_via_cash = payed_via_cash;
		this.payed_via_bank = payed_via_bank;
	}

	public int getExpense_id() {
		return expense_id;
	}

	public void setExpense_id(int expense_id) {
		this.expense_id = expense_id;
	}

	public int getBank_payment_mode() {
		return bank_payment_mode;
	}

	public void setBank_payment_mode(int bank_payment_mode) {
		this.bank_payment_mode = bank_payment_mode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getEnterprise_id() {
		return enterprise_id;
	}

	public void setEnterprise_id(String enterprise_id) {
		this.enterprise_id = enterprise_id;
	}

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getExpense_name() {
		return expense_name;
	}

	public void setExpense_name(String expense_name) {
		this.expense_name = expense_name;
	}

	public String getExpense_head() {
		return expense_head;
	}

	public void setExpense_head(String expense_head) {
		this.expense_head = expense_head;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPayed_via_cash() {
		return payed_via_cash;
	}

	public void setPayed_via_cash(boolean payed_via_cash) {
		this.payed_via_cash = payed_via_cash;
	}

	public boolean isPayed_via_bank() {
		return payed_via_bank;
	}

	public void setPayed_via_bank(boolean payed_via_bank) {
		this.payed_via_bank = payed_via_bank;
	}
	
}
