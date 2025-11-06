import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';

const ExpenseManager = () => {
  const [expenses, setExpenses] = useState([]);
  const [expense, setExpense] = useState({
    title: '', amount: '', category: '', date: '', notes: ''
  });
  const [idToFetch, setIdToFetch] = useState('');
  const [fetchedExpense, setFetchedExpense] = useState(null);
  const [message, setMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  const baseUrl = import.meta.env.VITE_API_URL || 'http://localhost:30030/expenseapi';

  useEffect(() => {
    fetchAllExpenses();
  }, []);

  const fetchAllExpenses = async () => {
    try {
      const res = await axios.get(`${baseUrl}/all`);
      setExpenses(res.data);
    } catch (err) {
      console.error(err);
      setMessage('Failed to fetch expenses.');
    }
  };

  const handleChange = (e) =>
    setExpense({ ...expense, [e.target.name]: e.target.value });

  const addExpense = async () => {
    try {
      await axios.post(`${baseUrl}/add`, {
        title: expense.title,
        category: expense.category,
        amount: Number(expense.amount),
        date: expense.date,
        notes: expense.notes
      });
      fetchAllExpenses();
      setExpense({ title: '', amount: '', category: '', date: '', notes: '' });
      setMessage('Expense added successfully!');
      setFetchedExpense(null);
    } catch (err) {
      console.error(err.response?.data || err.message);
      setMessage('Failed to add expense');
    }
  };

  const updateExpense = async () => {
    try {
      await axios.put(`${baseUrl}/update/${expense.id}`, {
        title: expense.title,
        category: expense.category,
        amount: Number(expense.amount),
        date: expense.date,
        notes: expense.notes
      });
      fetchAllExpenses();
      setEditMode(false);
      setExpense({ title: '', amount: '', category: '', date: '', notes: '' });
      setMessage('Expense updated successfully!');
      setFetchedExpense(null);
    } catch (err) {
      console.error(err.response?.data || err.message);
      setMessage('Failed to update expense');
    }
  };

  const deleteExpense = async (id) => {
    try {
      await axios.delete(`${baseUrl}/delete/${id}`);
      fetchAllExpenses();
      setMessage('Expense deleted successfully!');
      setFetchedExpense(null);
    } catch (err) {
      console.error(err.response?.data || err.message);
      setMessage('Failed to delete');
    }
  };

  const getExpenseById = async () => {
    try {
      const res = await axios.get(`${baseUrl}/get/${idToFetch}`);
      setFetchedExpense(res.data);
    } catch {
      setFetchedExpense(null);
      setMessage('Expense not found');
    }
  };

  const handleEdit = (exp) => {
    setExpense(exp);
    setEditMode(true);
  };

  return (
    <div>
      <h2>Expense Manager</h2>

      <div>
        <h3>{editMode ? 'Edit Expense' : 'Add Expense'}</h3>

        <input name="title" placeholder="Title" value={expense.title} onChange={handleChange} />
        <input name="category" placeholder="Category" value={expense.category} onChange={handleChange} />
        <input name="amount" placeholder="Amount" value={expense.amount} onChange={handleChange} />
        <input name="date" type="date" value={expense.date} onChange={handleChange} />
        <input name="notes" placeholder="Notes" value={expense.notes} onChange={handleChange} />

        {!editMode ? (
          <button onClick={addExpense}>Add</button>
        ) : (
          <button onClick={updateExpense}>Update</button>
        )}
      </div>

      <div>
        <h3>Get Expense By ID</h3>
        <input value={idToFetch} onChange={(e) => setIdToFetch(e.target.value)} placeholder="ID" />
        <button onClick={getExpenseById}>Fetch</button>
        {fetchedExpense && <pre>{JSON.stringify(fetchedExpense, null, 2)}</pre>}
      </div>

      <div>
        <h3>All Expenses</h3>
        {expenses.map((exp) => (
          <div key={exp.id}>
            {exp.id} - {exp.title} - {exp.category} - ₹{exp.amount} - {exp.date}
            <button onClick={() => handleEdit(exp)}>Edit</button>
            <button onClick={() => deleteExpense(exp.id)}>Delete</button>
          </div>
        ))}
      </div>

      {message && <p>{message}</p>}
    </div>
  );
};

export default ExpenseManager;
